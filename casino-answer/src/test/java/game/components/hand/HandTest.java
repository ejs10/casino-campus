package game.components.hand;

import game.components.card.Card;
import game.components.card.Rank;
import game.components.card.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Hand 클래스 테스트
 * 
 * <p>이 테스트는 Hand 클래스가 올바르게 구현되었는지 확인합니다.
 * 포커 게임의 핸드(손패) 관리와 족보 평가 기능을 검증합니다.</p>
 * 
 * <p>테스트 범위:</p>
 * <ul>
 *   <li>핸드 관리 기능 (add, clear, isFull)</li>
 *   <li>족보 평가 기능 (evaluate, open)</li>
 *   <li>족보 판정 헬퍼 메서드들</li>
 *   <li>핸드 비교 기능 (compareTo)</li>
 * </ul>
 * 
 * <p>테스트 항목:</p>
 * <ol>
 *   <li>카드 추가 테스트 - add() 메서드</li>
 *   <li>핸드 정리 테스트 - clear() 메서드</li>
 *   <li>핸드 가득 참 확인 - isFull() 메서드</li>
 *   <li>족보 평가 테스트 - evaluate() 메서드</li>
 *   <li>점수 반환 테스트 - open() 메서드</li>
 *   <li>핸드 비교 테스트 - compareTo() 메서드</li>
 *   <li>각 족보 판정 테스트</li>
 * </ol>
 */
public class HandTest {
    
    private Hand hand;
    
    @BeforeEach
    void setUp() {
        hand = new Hand();
    }
    
    @Test
    @DisplayName("1. 카드 추가 테스트 - add() 메서드가 올바르게 작동하는지 확인")
    void testAddCard() {
        // given (준비)
        Card card1 = new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.SPADES, Rank.KING);
        
        // when (실행)
        hand.add(card1);
        hand.add(card2);
        
        // then (검증)
        List<Card> cards = hand.getCards();
        assertEquals(2, cards.size(), 
            "add() 호출 후 카드 개수가 올바르지 않습니다.\n" +
            "예상: 2장, 실제: " + cards.size() + "장\n" +
            "add() 메서드에서 cards.add(card)를 호출했는지 확인하세요.");
        
        assertTrue(cards.contains(card1), 
            "추가한 첫 번째 카드가 핸드에 없습니다.\n" +
            "add() 메서드가 카드를 리스트에 추가하지 않았습니다.");
            
        assertTrue(cards.contains(card2), 
            "추가한 두 번째 카드가 핸드에 없습니다.");
    }
    
    @Test
    @DisplayName("2. null 카드 추가 방어 테스트")
    void testAddNullCard() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> hand.add(null),
            "null 카드를 추가할 때 IllegalArgumentException이 발생해야 합니다.\n" +
            "add() 메서드에서 if (card == null) 체크를 추가하세요.");
    }
    
    @Test
    @DisplayName("3. 핸드가 가득 찼을 때 카드 추가 테스트")
    void testAddCardWhenFull() {
        // given - 5장의 카드로 핸드를 가득 채움
        for (int i = 0; i < 5; i++) {
            hand.add(new Card(Suit.HEARTS, Rank.values()[i]));
        }
        
        // when & then - 6번째 카드 추가 시도
        Card extraCard = new Card(Suit.SPADES, Rank.ACE);
        assertThrows(IllegalStateException.class, () -> hand.add(extraCard),
            "핸드가 가득 찼을 때 카드를 추가하면 IllegalStateException이 발생해야 합니다.\n" +
            "add() 메서드에서 if (isFull()) 체크를 추가하세요.");
    }
    
    @Test
    @DisplayName("4. 핸드 정리 테스트 - clear() 메서드")
    void testClear() {
        // given - 카드 3장 추가
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        
        // when
        hand.clear();
        
        // then
        List<Card> cards = hand.getCards();
        assertEquals(0, cards.size(), 
            "clear() 호출 후 핸드가 비어있어야 합니다.\n" +
            "현재 카드 수: " + cards.size() + "\n" +
            "clear() 메서드에서 cards.clear()를 호출했는지 확인하세요.");
    }
    
    @Test
    @DisplayName("5. 핸드 가득 참 확인 테스트 - isFull() 메서드")
    void testIsFull() {
        // 처음에는 가득 차지 않음
        assertFalse(hand.isFull(), 
            "빈 핸드는 가득 차지 않았어야 합니다.\n" +
            "isFull()이 true를 반환했습니다.");
        
        // 4장 추가 - 아직 가득 차지 않음
        for (int i = 0; i < 4; i++) {
            hand.add(new Card(Suit.HEARTS, Rank.values()[i]));
        }
        assertFalse(hand.isFull(), 
            "4장의 카드를 가진 핸드는 가득 차지 않았어야 합니다.");
        
        // 5장째 추가 - 이제 가득 참
        hand.add(new Card(Suit.HEARTS, Rank.values()[4]));
        assertTrue(hand.isFull(), 
            "5장의 카드를 가진 핸드는 가득 차야 합니다.\n" +
            "isFull()이 false를 반환했습니다.\n" +
            "return cards.size() == MAX_CARDS; 를 확인하세요.");
    }
    
    @Test
    @DisplayName("6. getCards() 수정 불가능한 리스트 반환 테스트")
    void testGetCardsUnmodifiableList() {
        // given
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        hand.add(card);
        
        // when - getCards()로 리스트를 가져와서 수정 시도
        List<Card> cards = hand.getCards();
        
        // then - 리스트 수정 시도 시 예외 발생
        assertThrows(UnsupportedOperationException.class, () -> cards.clear(),
            "getCards()가 반환한 리스트는 수정할 수 없어야 합니다.\n" +
            "List.copyOf()를 사용하여 불변 리스트를 반환하세요.");
        
        // 원본 핸드는 여전히 카드를 가지고 있어야 함
        assertEquals(1, hand.getCards().size(), 
            "원본 핸드의 카드는 영향받지 않아야 합니다.");
    }
    
    @Test
    @DisplayName("7. 족보 평가 예외 테스트 - 5장이 아닐 때")
    void testEvaluateWithWrongNumberOfCards() {
        // 카드가 4장일 때
        for (int i = 0; i < 4; i++) {
            hand.add(new Card(Suit.HEARTS, Rank.values()[i]));
        }
        
        assertThrows(IllegalStateException.class, () -> hand.evaluate(),
            "카드가 5장이 아닐 때 evaluate()는 IllegalStateException을 발생시켜야 합니다.\n" +
            "현재 카드 수: " + hand.getCards().size() + "\n" +
            "evaluate() 메서드 시작 부분에서 카드 수를 확인하세요.");
    }
    
    @Test
    @DisplayName("8. 플러시 판정 테스트 - isFlush()")
    void testIsFlush() {
        // given - 모두 하트인 5장
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.FLUSH, rank,
            "모든 카드가 같은 무늬일 때 FLUSH를 반환해야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "isFlush() 메서드가 올바르게 구현되었는지 확인하세요.");
    }
    
    @Test
    @DisplayName("9. 포카드 판정 테스트 - isFourOfAKind()")
    void testFourOfAKind() {
        // given - 에이스 4장 + 킹 1장
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.DIAMONDS, Rank.ACE));
        hand.add(new Card(Suit.CLUBS, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.FOUR_OF_A_KIND, rank,
            "같은 랭크 4장이 있을 때 FOUR_OF_A_KIND를 반환해야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "isFourOfAKind() 메서드가 이미 구현되어 있으니 evaluate()에서 올바르게 호출하는지 확인하세요.");
    }
    
    @Test
    @DisplayName("10. 원페어 판정 테스트 - isOnePair()")
    void testOnePair() {
        // given - 에이스 2장 + 서로 다른 3장
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.DIAMONDS, Rank.KING));
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.ONE_PAIR, rank,
            "같은 랭크 2장이 있을 때 ONE_PAIR를 반환해야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "isOnePair() 메서드가 이미 구현되어 있으니 evaluate()에서 올바르게 호출하는지 확인하세요.");
    }
    
    @Test
    @DisplayName("11. 하이카드 판정 테스트")
    void testHighCard() {
        // given - 아무 조합도 없는 5장
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        hand.add(new Card(Suit.CLUBS, Rank.JACK));
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.HIGH_CARD, rank,
            "아무런 조합이 없을 때 HIGH_CARD를 반환해야 합니다.\n" +
            "실제 반환값: " + rank);
    }
    
    @Test
    @DisplayName("12. open() 메서드 테스트 - 점수 반환")
    void testOpen() {
        // given - 플러시 핸드
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        
        // when
        int score = hand.open();
        
        // then
        assertEquals(600, score,
            "플러시의 점수는 600이어야 합니다.\n" +
            "실제 점수: " + score + "\n" +
            "open() 메서드는 evaluate().getScore()를 반환해야 합니다.");
    }
    
    @Test
    @DisplayName("13. compareTo() 메서드 테스트 - 핸드 비교")
    void testCompareTo() {
        // given - 첫 번째 핸드: 원페어
        Hand hand1 = new Hand();
        hand1.add(new Card(Suit.HEARTS, Rank.ACE));
        hand1.add(new Card(Suit.SPADES, Rank.ACE));
        hand1.add(new Card(Suit.DIAMONDS, Rank.KING));
        hand1.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand1.add(new Card(Suit.HEARTS, Rank.JACK));
        
        // 두 번째 핸드: 투페어
        Hand hand2 = new Hand();
        hand2.add(new Card(Suit.HEARTS, Rank.KING));
        hand2.add(new Card(Suit.SPADES, Rank.KING));
        hand2.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        hand2.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand2.add(new Card(Suit.HEARTS, Rank.JACK));
        
        // when & then
        assertTrue(hand1.compareTo(hand2) < 0,
            "원페어는 투페어보다 약해야 합니다.\n" +
            "compareTo() 결과: " + hand1.compareTo(hand2) + " (음수여야 함)\n" +
            "compareTo()는 this.open()과 other.open()을 비교해야 합니다.");
            
        assertTrue(hand2.compareTo(hand1) > 0,
            "투페어는 원페어보다 강해야 합니다.\n" +
            "compareTo() 결과: " + hand2.compareTo(hand1) + " (양수여야 함)");
            
        assertEquals(0, hand1.compareTo(hand1),
            "같은 핸드끼리 비교하면 0을 반환해야 합니다.");
    }
    
    @Test
    @DisplayName("14. toString() 메서드 테스트")
    void testToString() {
        // given
        Card card1 = new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.SPADES, Rank.KING);
        hand.add(card1);
        hand.add(card2);
        
        // when
        String result = hand.toString();
        
        // then
        assertNotNull(result, "toString()은 null을 반환하면 안 됩니다.");
        assertTrue(result.contains("[") && result.contains("]"),
            "toString() 결과는 대괄호로 둘러싸여야 합니다.\n" +
            "실제 결과: " + result + "\n" +
            "cards.toString()을 반환하면 됩니다.");
    }
    
    @Test
    @DisplayName("15. 로열 플러시 판정 테스트 - isRoyalFlush() 구현 필요")
    void testRoyalFlush() {
        // given - 스페이드 10, J, Q, K, A
        hand.add(new Card(Suit.SPADES, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.SPADES, Rank.QUEEN));
        hand.add(new Card(Suit.SPADES, Rank.JACK));
        hand.add(new Card(Suit.SPADES, Rank.TEN));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.ROYAL_FLUSH, rank,
            "같은 무늬의 10, J, Q, K, A는 로열 플러시여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isRoyalFlush() 메서드를 구현하세요.\n" +
            "힌트: isFlush()가 true이고, 10, J, Q, K, A가 모두 있는지 확인");
    }
    
    @Test
    @DisplayName("16. 스트레이트 플러시 판정 테스트 - isStraightFlush() 구현 필요")
    void testStraightFlush() {
        // given - 하트 5, 6, 7, 8, 9
        hand.add(new Card(Suit.HEARTS, Rank.FIVE));
        hand.add(new Card(Suit.HEARTS, Rank.SIX));
        hand.add(new Card(Suit.HEARTS, Rank.SEVEN));
        hand.add(new Card(Suit.HEARTS, Rank.EIGHT));
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.STRAIGHT_FLUSH, rank,
            "같은 무늬의 연속된 5장은 스트레이트 플러시여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isStraightFlush() 메서드를 구현하세요.\n" +
            "힌트: isFlush() && isStraight()");
    }
    
    @Test
    @DisplayName("17. 풀하우스 판정 테스트 - isFullHouse() 구현 필요")
    void testFullHouse() {
        // given - K 3장, Q 2장
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.DIAMONDS, Rank.KING));
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.QUEEN));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.FULL_HOUSE, rank,
            "3장 + 2장 조합은 풀하우스여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isFullHouse() 메서드를 구현하세요.\n" +
            "힌트: getRankCounts()로 개수를 세고, 3과 2가 모두 있는지 확인");
    }
    
    @Test
    @DisplayName("18. 스트레이트 판정 테스트 - isStraight() 구현 필요")
    void testStraight() {
        // given - 5, 6, 7, 8, 9 (다른 무늬)
        hand.add(new Card(Suit.HEARTS, Rank.FIVE));
        hand.add(new Card(Suit.SPADES, Rank.SIX));
        hand.add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        hand.add(new Card(Suit.CLUBS, Rank.EIGHT));
        hand.add(new Card(Suit.HEARTS, Rank.NINE));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.STRAIGHT, rank,
            "연속된 5장의 카드는 스트레이트여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isStraight() 메서드를 구현하세요.\n" +
            "힌트: 카드를 값으로 정렬 후 연속성 확인");
    }
    
    @Test
    @DisplayName("19. 백스트레이트(A-2-3-4-5) 판정 테스트 - isStraight() 특수 케이스")
    void testAceLowStraight() {
        // given - A, 2, 3, 4, 5 (백스트레이트)
        hand.add(new Card(Suit.HEARTS, Rank.ACE));
        hand.add(new Card(Suit.SPADES, Rank.TWO));
        hand.add(new Card(Suit.DIAMONDS, Rank.THREE));
        hand.add(new Card(Suit.CLUBS, Rank.FOUR));
        hand.add(new Card(Suit.HEARTS, Rank.FIVE));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.STRAIGHT, rank,
            "A-2-3-4-5는 백스트레이트로 인정되어야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 특수 케이스 처리 필요 ===\n" +
            "isStraight()에서 [2,3,4,5,14] 패턴을 별도로 확인하세요.");
    }
    
    @Test
    @DisplayName("20. 쓰리카드 판정 테스트 - isThreeOfAKind() 구현 필요")
    void testThreeOfAKind() {
        // given - J 3장 + 다른 2장
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        hand.add(new Card(Suit.SPADES, Rank.JACK));
        hand.add(new Card(Suit.DIAMONDS, Rank.JACK));
        hand.add(new Card(Suit.CLUBS, Rank.NINE));
        hand.add(new Card(Suit.HEARTS, Rank.SEVEN));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.THREE_OF_A_KIND, rank,
            "같은 랭크 3장은 쓰리카드여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isThreeOfAKind() 메서드를 구현하세요.\n" +
            "힌트: getRankCounts()의 결과에서 3이 있는지 확인");
    }
    
    @Test
    @DisplayName("21. 투페어 판정 테스트 - isTwoPair() 구현 필요")
    void testTwoPair() {
        // given - K 2장, Q 2장, J 1장
        hand.add(new Card(Suit.HEARTS, Rank.KING));
        hand.add(new Card(Suit.SPADES, Rank.KING));
        hand.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        hand.add(new Card(Suit.CLUBS, Rank.QUEEN));
        hand.add(new Card(Suit.HEARTS, Rank.JACK));
        
        // when
        HandRank rank = hand.evaluate();
        
        // then
        assertEquals(HandRank.TWO_PAIR, rank,
            "페어가 2개 있으면 투페어여야 합니다.\n" +
            "실제 반환값: " + rank + "\n" +
            "=== 구현 필요 ===\n" +
            "Hand.java의 isTwoPair() 메서드를 구현하세요.\n" +
            "힌트: getRankCounts()의 values에서 2가 몇 개인지 세기");
    }
    
    @Test
    @DisplayName("22. 모든 족보가 아닌 경우들 - 엣지 케이스")
    void testNotStraight() {
        // A, K, Q, J, 9는 스트레이트가 아님 (10이 없음)
        Hand notStraight = new Hand();
        notStraight.add(new Card(Suit.HEARTS, Rank.ACE));
        notStraight.add(new Card(Suit.SPADES, Rank.KING));
        notStraight.add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        notStraight.add(new Card(Suit.CLUBS, Rank.JACK));
        notStraight.add(new Card(Suit.HEARTS, Rank.NINE));
        
        assertNotEquals(HandRank.STRAIGHT, notStraight.evaluate(),
            "A-K-Q-J-9는 스트레이트가 아닙니다 (10이 없음).\n" +
            "isStraight()가 연속성을 올바르게 확인하는지 검증하세요.");
    }
    
    @Test
    @DisplayName("23. 포커 게임 시뮬레이션 - 다양한 핸드 대결")
    void testCompletePokerSimulation() {
        // 각 족보별 대표 핸드 생성
        Hand[] hands = new Hand[9];
        String[] handNames = new String[9];
        
        // 로열 플러시
        hands[0] = new Hand();
        hands[0].add(new Card(Suit.SPADES, Rank.ACE));
        hands[0].add(new Card(Suit.SPADES, Rank.KING));
        hands[0].add(new Card(Suit.SPADES, Rank.QUEEN));
        hands[0].add(new Card(Suit.SPADES, Rank.JACK));
        hands[0].add(new Card(Suit.SPADES, Rank.TEN));
        handNames[0] = "로열 플러시";
        
        // 스트레이트 플러시
        hands[1] = new Hand();
        hands[1].add(new Card(Suit.HEARTS, Rank.NINE));
        hands[1].add(new Card(Suit.HEARTS, Rank.EIGHT));
        hands[1].add(new Card(Suit.HEARTS, Rank.SEVEN));
        hands[1].add(new Card(Suit.HEARTS, Rank.SIX));
        hands[1].add(new Card(Suit.HEARTS, Rank.FIVE));
        handNames[1] = "스트레이트 플러시";
        
        // 포카드
        hands[2] = new Hand();
        hands[2].add(new Card(Suit.HEARTS, Rank.KING));
        hands[2].add(new Card(Suit.SPADES, Rank.KING));
        hands[2].add(new Card(Suit.DIAMONDS, Rank.KING));
        hands[2].add(new Card(Suit.CLUBS, Rank.KING));
        hands[2].add(new Card(Suit.HEARTS, Rank.QUEEN));
        handNames[2] = "포카드";
        
        // 풀하우스
        hands[3] = new Hand();
        hands[3].add(new Card(Suit.HEARTS, Rank.JACK));
        hands[3].add(new Card(Suit.SPADES, Rank.JACK));
        hands[3].add(new Card(Suit.DIAMONDS, Rank.JACK));
        hands[3].add(new Card(Suit.CLUBS, Rank.TEN));
        hands[3].add(new Card(Suit.HEARTS, Rank.TEN));
        handNames[3] = "풀하우스";
        
        // 플러시
        hands[4] = new Hand();
        hands[4].add(new Card(Suit.DIAMONDS, Rank.ACE));
        hands[4].add(new Card(Suit.DIAMONDS, Rank.QUEEN));
        hands[4].add(new Card(Suit.DIAMONDS, Rank.TEN));
        hands[4].add(new Card(Suit.DIAMONDS, Rank.FIVE));
        hands[4].add(new Card(Suit.DIAMONDS, Rank.THREE));
        handNames[4] = "플러시";
        
        // 스트레이트
        hands[5] = new Hand();
        hands[5].add(new Card(Suit.HEARTS, Rank.TEN));
        hands[5].add(new Card(Suit.SPADES, Rank.NINE));
        hands[5].add(new Card(Suit.DIAMONDS, Rank.EIGHT));
        hands[5].add(new Card(Suit.CLUBS, Rank.SEVEN));
        hands[5].add(new Card(Suit.HEARTS, Rank.SIX));
        handNames[5] = "스트레이트";
        
        // 쓰리카드
        hands[6] = new Hand();
        hands[6].add(new Card(Suit.HEARTS, Rank.NINE));
        hands[6].add(new Card(Suit.SPADES, Rank.NINE));
        hands[6].add(new Card(Suit.DIAMONDS, Rank.NINE));
        hands[6].add(new Card(Suit.CLUBS, Rank.FIVE));
        hands[6].add(new Card(Suit.HEARTS, Rank.TWO));
        handNames[6] = "쓰리카드";
        
        // 투페어
        hands[7] = new Hand();
        hands[7].add(new Card(Suit.HEARTS, Rank.EIGHT));
        hands[7].add(new Card(Suit.SPADES, Rank.EIGHT));
        hands[7].add(new Card(Suit.DIAMONDS, Rank.SEVEN));
        hands[7].add(new Card(Suit.CLUBS, Rank.SEVEN));
        hands[7].add(new Card(Suit.HEARTS, Rank.ACE));
        handNames[7] = "투페어";
        
        // 원페어
        hands[8] = new Hand();
        hands[8].add(new Card(Suit.HEARTS, Rank.SIX));
        hands[8].add(new Card(Suit.SPADES, Rank.SIX));
        hands[8].add(new Card(Suit.DIAMONDS, Rank.KING));
        hands[8].add(new Card(Suit.CLUBS, Rank.QUEEN));
        hands[8].add(new Card(Suit.HEARTS, Rank.JACK));
        handNames[8] = "원페어";
        
        // 모든 핸드가 올바른 순서인지 확인
        for (int i = 0; i < hands.length - 1; i++) {
            assertTrue(hands[i].compareTo(hands[i + 1]) > 0,
                handNames[i] + "(" + hands[i].open() + "점)는 " + 
                handNames[i + 1] + "(" + hands[i + 1].open() + "점)보다 강해야 합니다.\n" +
                "compareTo 결과: " + hands[i].compareTo(hands[i + 1]));
        }
        
        // 각 핸드의 족보가 올바른지 확인
        assertEquals(HandRank.ROYAL_FLUSH, hands[0].evaluate(), "로열 플러시 판정 실패");
        assertEquals(HandRank.STRAIGHT_FLUSH, hands[1].evaluate(), "스트레이트 플러시 판정 실패");
        assertEquals(HandRank.FOUR_OF_A_KIND, hands[2].evaluate(), "포카드 판정 실패");
        assertEquals(HandRank.FULL_HOUSE, hands[3].evaluate(), "풀하우스 판정 실패");
        assertEquals(HandRank.FLUSH, hands[4].evaluate(), "플러시 판정 실패");
        assertEquals(HandRank.STRAIGHT, hands[5].evaluate(), "스트레이트 판정 실패");
        assertEquals(HandRank.THREE_OF_A_KIND, hands[6].evaluate(), "쓰리카드 판정 실패");
        assertEquals(HandRank.TWO_PAIR, hands[7].evaluate(), "투페어 판정 실패");
        assertEquals(HandRank.ONE_PAIR, hands[8].evaluate(), "원페어 판정 실패");
    }
}