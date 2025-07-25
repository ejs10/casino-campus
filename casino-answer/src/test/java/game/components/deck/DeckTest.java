package game.components.deck;

import game.components.card.Card;
import game.components.card.Rank;
import game.components.card.Suit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Deck 클래스 테스트
 * 
 * 이 테스트 클래스는 Deck 클래스의 모든 기능이 올바르게 구현되었는지 검증합니다.
 * private 필드를 테스트하기 위해 리플렉션을 사용하여 내부 상태를 확인합니다.
 * 
 * <p>테스트 범위:</p>
 * <ul>
 *   <li>덱 초기화: 52장 카드 생성, 중복 검사, 모든 카드 존재 확인</li>
 *   <li>셔플 기능: 카드 순서 변경, 카드 수 유지, 모든 카드 보존</li>
 *   <li>카드 뽑기: 카드 제거, 예외 처리, 연속 뽑기</li>
 *   <li>빈 덱 감지: isEmpty() 메서드 동작</li>
 *   <li>실제 게임 시나리오: 블랙잭 게임 시뮬레이션</li>
 * </ul>
 * 
 * <p>테스트 항목:</p>
 * <ol>
 *   <li>덱 초기화 테스트 - 52장의 카드가 올바르게 생성되는지</li>
 *   <li>shuffle() 테스트 - 카드가 올바르게 섞이는지</li>
 *   <li>drawCard() 테스트 - 카드를 올바르게 뽑는지</li>
 *   <li>drawCard() 예외 테스트 - 빈 덱에서 카드를 뽑을 때</li>
 *   <li>isEmpty() 테스트 - 빈 덱을 올바르게 감지하는지</li>
 *   <li>연속 카드 뽑기 테스트 - 여러 장을 연속으로 뽑을 때</li>
 *   <li>실제 게임 시나리오 테스트 - 블랙잭 게임 시뮬레이션</li>
 * </ol>
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class DeckTest {

    /**
     * 리플렉션을 사용하여 private cards 필드에 접근하는 헬퍼 메서드
     * 
     * <p>테스트 목적으로만 사용되며, 실제 코드에서는 사용하지 않아야 합니다.</p>
     * 
     * @param deck 카드 리스트를 가져올 Deck 객체
     * @return Deck 객체의 cards 필드
     * @throws RuntimeException 리플렉션 접근 실패 시
     */
    @SuppressWarnings("unchecked")
    private List<Card> getCardsFromDeck(Deck deck) {
        try {
            Field cardsField = Deck.class.getDeclaredField("cards");
            cardsField.setAccessible(true);
            return (List<Card>) cardsField.get(deck);
        } catch (Exception e) {
            throw new RuntimeException("리플렉션을 통한 cards 필드 접근 실패", e);
        }
    }
    
    @Test
    @DisplayName("1. 덱 초기화 테스트 - 52장의 카드가 올바르게 생성되는지 확인")
    void testDeckInitialization() {
        // when (실행) - 새 덱을 생성합니다
        Deck deck = new Deck();
        
        // then (검증) - 리플렉션으로 cards 필드에 접근하여 확인
        List<Card> cards = getCardsFromDeck(deck);
        
        // 카드 개수 확인
        assertEquals(52, cards.size(), 
            "덱은 52장의 카드를 가져야 합니다.\n" +
            "현재 카드 수: " + cards.size() + "\n" +
            "힌트: 4개 무늬 × 13개 랭크 = 52장\n" +
            "구현 확인사항:\n" +
            "- Suit.values()로 모든 무늬를 순회했나요?\n" +
            "- Rank.values()로 모든 랭크를 순회했나요?\n" +
            "- 이중 반복문을 사용했나요?");
        
        // 중복 카드 확인
        Set<String> uniqueCards = new HashSet<>();
        for (Card card : cards) {
            String cardString = card.getSuit() + "-" + card.getRank();
            assertFalse(uniqueCards.contains(cardString), 
                "중복된 카드가 발견되었습니다: " + cardString + "\n" +
                "각 무늬와 랭크 조합은 한 번씩만 나타나야 합니다.");
            uniqueCards.add(cardString);
        }
        
        // 모든 카드가 존재하는지 확인
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                boolean found = false;
                for (Card card : cards) {
                    if (card.getSuit() == suit && card.getRank() == rank) {
                        found = true;
                        break;
                    }
                }
                assertTrue(found, 
                    "다음 카드가 덱에 없습니다: " + rank + " of " + suit + "\n" +
                    "모든 무늬와 랭크 조합이 덱에 포함되어야 합니다.");
            }
        }
    }
    
    @Test
    @DisplayName("2. shuffle() 테스트 - 카드가 올바르게 섞이는지 확인")
    void testShuffle() {
        // given (준비) - 두 개의 덱을 생성
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        
        // 셔플 전 카드 순서 저장
        List<Card> beforeShuffle = getCardsFromDeck(deck1);
        StringBuilder orderBefore = new StringBuilder();
        for (Card card : beforeShuffle) {
            orderBefore.append(card.toString()).append(",");
        }
        
        // when (실행) - 두 번째 덱을 셔플
        deck2.shuffle();
        
        // then (검증)
        List<Card> afterShuffle = getCardsFromDeck(deck2);
        
        // 카드 수가 변하지 않았는지 확인
        assertEquals(52, afterShuffle.size(), 
            "shuffle() 후에도 카드는 52장이어야 합니다.\n" +
            "현재 카드 수: " + afterShuffle.size() + "\n" +
            "셔플 과정에서 카드를 추가하거나 제거하면 안 됩니다.");
        
        // 카드 순서가 변경되었는지 확인
        StringBuilder orderAfter = new StringBuilder();
        for (Card card : afterShuffle) {
            orderAfter.append(card.toString()).append(",");
        }
        
        assertNotEquals(orderBefore.toString(), orderAfter.toString(),
            "shuffle() 호출 후 카드 순서가 변경되지 않았습니다.\n" +
            "힌트: Collections.shuffle() 메서드를 사용하세요.\n" +
            "구현 예시: Collections.shuffle(cards);");
            
        // 여전히 모든 카드가 존재하는지 확인
        Set<String> cardsAfterShuffle = new HashSet<>();
        for (Card card : afterShuffle) {
            cardsAfterShuffle.add(card.getSuit() + "-" + card.getRank());
        }
        assertEquals(52, cardsAfterShuffle.size(), 
            "셔플 후 일부 카드가 사라지거나 중복되었습니다.");
    }
    
    @Test
    @DisplayName("3. drawCard() 테스트 - 카드를 올바르게 뽑는지 확인")
    void testDrawCard() {
        // given (준비)
        Deck deck = new Deck();
        int initialSize = getCardsFromDeck(deck).size();
        
        // when (실행) - 카드를 한 장 뽑기
        Card drawnCard = deck.drawCard();
        
        // then (검증)
        assertNotNull(drawnCard, 
            "drawCard()는 null을 반환하면 안 됩니다.\n" +
            "카드를 제거하고 반환했는지 확인하세요.");
            
        List<Card> remainingCards = getCardsFromDeck(deck);
        assertEquals(initialSize - 1, remainingCards.size(), 
            "drawCard() 호출 후 덱의 카드 수가 1장 줄어야 합니다.\n" +
            "이전 카드 수: " + initialSize + "\n" +
            "현재 카드 수: " + remainingCards.size() + "\n" +
            "힌트: cards.remove(0) 또는 cards.remove(cards.size()-1)을 사용하세요.");
        
        // 뽑은 카드가 덱에서 제거되었는지 확인
        for (Card card : remainingCards) {
            assertNotSame(drawnCard, card, 
                "뽑은 카드가 여전히 덱에 있습니다.\n" +
                "remove() 메서드를 호출하여 카드를 제거했는지 확인하세요.");
        }
    }
    
    @Test
    @DisplayName("4. drawCard() 예외 테스트 - 빈 덱에서 카드를 뽑을 때")
    void testDrawCardFromEmptyDeck() {
        // given (준비) - 모든 카드를 뽑아서 빈 덱 만들기
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        
        // when & then - 빈 덱에서 카드를 뽑으면 예외 발생
        assertThrows(IllegalStateException.class, () -> deck.drawCard(),
            "빈 덱에서 drawCard()를 호출하면 IllegalStateException이 발생해야 합니다.\n" +
            "구현 힌트:\n" +
            "1. if (isEmpty()) 체크를 먼저 하세요\n" +
            "2. throw new IllegalStateException(\"덱이 비어있습니다\")");
    }
    
    @Test
    @DisplayName("5. isEmpty() 테스트 - 덱이 비어있는지 올바르게 확인")
    void testIsEmpty() {
        // given (준비)
        Deck deck = new Deck();
        
        // 처음에는 비어있지 않아야 함
        assertFalse(deck.isEmpty(), 
            "새로 생성된 덱은 비어있지 않아야 합니다.\n" +
            "isEmpty()가 true를 반환했습니다.\n" +
            "힌트: return cards.isEmpty();");
        
        // 모든 카드를 뽑은 후
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        
        // 이제는 비어있어야 함
        assertTrue(deck.isEmpty(), 
            "52장을 모두 뽑은 후 덱은 비어있어야 합니다.\n" +
            "isEmpty()가 false를 반환했습니다.\n" +
            "cards 리스트가 비었는지 확인하세요.");
    }
    
    @Test
    @DisplayName("6. 연속 카드 뽑기 테스트 - 여러 장을 연속으로 뽑을 때")
    void testMultipleDraws() {
        // given (준비)
        Deck deck = new Deck();
        Set<Card> drawnCards = new HashSet<>();
        
        // when (실행) - 5장 연속으로 뽑기
        for (int i = 0; i < 5; i++) {
            Card card = deck.drawCard();
            
            // 이미 뽑은 카드가 다시 나오면 안 됨
            assertFalse(drawnCards.contains(card), 
                "이미 뽑은 카드가 다시 나왔습니다: " + card + "\n" +
                "drawCard()에서 카드를 제거하지 않았거나,\n" +
                "동일한 카드를 여러 번 생성했을 가능성이 있습니다.");
            
            drawnCards.add(card);
        }
        
        // then (검증)
        assertEquals(47, getCardsFromDeck(deck).size(), 
            "5장을 뽑은 후 덱에는 47장이 남아야 합니다.");
    }
    
    @Test
    @DisplayName("7. 실제 게임 시나리오 테스트 - 블랙잭 게임 시뮬레이션")
    void testBlackjackScenario() {
        // 시나리오: 블랙잭 게임에서 플레이어와 딜러에게 카드 배분
        Deck deck = new Deck();
        deck.shuffle();
        
        // 플레이어에게 2장
        Card playerCard1 = deck.drawCard();
        Card playerCard2 = deck.drawCard();
        
        // 딜러에게 2장
        Card dealerCard1 = deck.drawCard();
        Card dealerCard2 = deck.drawCard();
        
        // 검증
        assertNotNull(playerCard1, "플레이어 첫 번째 카드가 null입니다.");
        assertNotNull(playerCard2, "플레이어 두 번째 카드가 null입니다.");
        assertNotNull(dealerCard1, "딜러 첫 번째 카드가 null입니다.");
        assertNotNull(dealerCard2, "딜러 두 번째 카드가 null입니다.");
        
        // 4장이 모두 다른 카드여야 함
        Set<Card> dealtCards = Set.of(playerCard1, playerCard2, dealerCard1, dealerCard2);
        assertEquals(4, dealtCards.size(), 
            "배분된 4장의 카드는 모두 달라야 합니다.\n" +
            "drawCard()가 올바르게 카드를 제거하는지 확인하세요.");
        
        // 덱에는 48장이 남아야 함
        assertEquals(48, getCardsFromDeck(deck).size(), 
            "4장을 배분한 후 덱에는 48장이 남아야 합니다.");
    }
}