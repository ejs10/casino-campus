package game.components.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Card 클래스 테스트
 * 
 * 이 테스트 클래스는 Card 클래스의 모든 기능이 올바르게 구현되었는지 검증합니다.
 * 학생들이 구현해야 할 메서드들이 정확히 작동하는지 체계적으로 테스트합니다.
 * 
 * <p>테스트 범위:</p>
 * <ul>
 *   <li>기본 기능: 생성자, getter 메서드</li>
 *   <li>핵심 기능: getValue(), toString(), compareTo()</li>
 *   <li>동등성 비교: equals(), hashCode()</li>
 *   <li>예외 처리: null 입력 방어</li>
 *   <li>실전 시나리오: 포커 게임에서의 카드 비교</li>
 * </ul>
 * 
 * <p>테스트 항목:</p>
 * <ol>
 *   <li>생성자 테스트 - 카드가 올바른 무늬와 랭크로 생성되는지</li>
 *   <li>null 입력 방어 테스트 - 생성자가 null을 올바르게 처리하는지</li>
 *   <li>toString() 테스트 - 카드가 올바른 형식으로 출력되는지</li>
 *   <li>getValue() 테스트 - 각 카드의 포커 순위 값이 올바른지</li>
 *   <li>compareTo() 테스트 (다른 랭크) - 다른 랭크의 카드 비교</li>
 *   <li>compareTo() 테스트 (같은 랭크) - 같은 랭크일 때 무늬 비교</li>
 *   <li>equals() 테스트 - 카드 동등성 비교</li>
 *   <li>hashCode() 테스트 - equals()와 일관성 확인</li>
 *   <li>실제 게임 시나리오 테스트 - 포커 게임에서의 카드 비교</li>
 * </ol>
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class CardTest {
    
    @Test
    @DisplayName("1. 카드 생성 테스트 - 카드가 지정한 무늬와 랭크로 올바르게 생성되는지 확인")
    void testCardCreation() {
        // given (준비) - 테스트할 무늬와 랭크를 준비합니다
        Suit expectedSuit = Suit.SPADES;     // 예상 무늬: 스페이드
        Rank expectedRank = Rank.ACE;        // 예상 랭크: 에이스
        
        // when (실행) - Card 생성자를 호출합니다
        Card card = new Card(expectedSuit, expectedRank);
        
        // then (검증) - 생성된 카드의 속성을 확인합니다
        assertEquals(expectedSuit, card.getSuit(), 
            "카드의 무늬가 생성자에서 설정한 무늬와 다릅니다.\n" +
            "예상: " + expectedSuit + ", 실제: " + card.getSuit() + "\n" +
            "Card 생성자에서 this.suit = suit; 가 올바르게 설정되었는지 확인하세요.");
            
        assertEquals(expectedRank, card.getRank(), 
            "카드의 랭크가 생성자에서 설정한 랭크와 다릅니다.\n" +
            "예상: " + expectedRank + ", 실제: " + card.getRank() + "\n" +
            "Card 생성자에서 this.rank = rank; 가 올바르게 설정되었는지 확인하세요.");
    }
    
    @Test
    @DisplayName("2. null 입력 방어 테스트 - 생성자가 null을 올바르게 처리하는지 확인")
    void testCardCreationWithNull() {
        // 무늬가 null일 때
        assertThrows(IllegalArgumentException.class, () -> new Card(null, Rank.ACE),
            "무늬(Suit)가 null일 때 IllegalArgumentException이 발생해야 합니다.\n" +
            "생성자에서 if (suit == null) 체크를 추가하세요.");
            
        // 랭크가 null일 때
        assertThrows(IllegalArgumentException.class, () -> new Card(Suit.SPADES, null),
            "랭크(Rank)가 null일 때 IllegalArgumentException이 발생해야 합니다.\n" +
            "생성자에서 if (rank == null) 체크를 추가하세요.");
    }
    
    @Test
    @DisplayName("3. toString() 테스트 - 카드가 올바른 형식으로 출력되는지 확인")
    void testCardToString() {
        // 테스트 케이스 1: 스페이드 에이스
        Card aceOfSpades = new Card(Suit.SPADES, Rank.ACE);
        String result1 = aceOfSpades.toString();
        
        // toString()은 "랭크심볼+무늬기호" 형식이어야 합니다
        assertTrue(result1.contains("A") && result1.contains("♠"),
            "스페이드 에이스의 toString() 결과가 올바르지 않습니다.\n" +
            "예상 형식: 'A♠' (A와 ♠가 포함되어야 함)\n" +
            "실제 결과: '" + result1 + "'\n" +
            "힌트: rank.getSymbol() + suit.getSymbol()을 사용하세요.");
        
        // 테스트 케이스 2: 하트 10
        Card tenOfHearts = new Card(Suit.HEARTS, Rank.TEN);
        String result2 = tenOfHearts.toString();
        assertTrue(result2.contains("10") && result2.contains("♥"), 
            "하트 10의 toString() 결과가 올바르지 않습니다.\n" +
            "예상 형식: '10♥' (10과 ♥가 포함되어야 함)\n" +
            "실제 결과: '" + result2 + "'");
    }
    
    @Test
    @DisplayName("4. getValue() 테스트 - 포커 게임에서의 카드 값이 올바른지 확인")
    void testGetValue() {
        // 각 랭크별 예상 값
        assertEquals(2, new Card(Suit.SPADES, Rank.TWO).getValue(), 
            "TWO의 getValue()는 2를 반환해야 합니다.\n" +
            "힌트: return rank.getValue(); 를 사용하세요.");
            
        assertEquals(10, new Card(Suit.HEARTS, Rank.TEN).getValue(), 
            "TEN의 getValue()는 10을 반환해야 합니다.");
            
        assertEquals(11, new Card(Suit.DIAMONDS, Rank.JACK).getValue(), 
            "JACK의 getValue()는 11을 반환해야 합니다.");
            
        assertEquals(14, new Card(Suit.CLUBS, Rank.ACE).getValue(), 
            "ACE의 getValue()는 14를 반환해야 합니다.\n" +
            "ACE는 포커에서 가장 강한 카드입니다.");
    }
    
    @Test
    @DisplayName("5. compareTo() 테스트 - 다른 랭크의 카드 비교")
    void testCompareToWithDifferentRanks() {
        Card ace = new Card(Suit.SPADES, Rank.ACE);      // 가장 강한 카드
        Card king = new Card(Suit.HEARTS, Rank.KING);    // 두 번째로 강한 카드
        Card two = new Card(Suit.DIAMONDS, Rank.TWO);    // 가장 약한 카드
        
        // ACE > KING
        assertTrue(ace.compareTo(king) > 0, 
            "compareTo() 오류: ACE가 KING보다 큰 값을 반환해야 합니다.\n" +
            "ACE.compareTo(KING)의 결과: " + ace.compareTo(king) + "\n" +
            "양수가 나와야 합니다.\n" +
            "힌트: 먼저 Rank를 비교하세요. this.rank.compareTo(other.getRank())");
        
        // KING > TWO
        assertTrue(king.compareTo(two) > 0, 
            "compareTo() 오류: KING이 TWO보다 큰 값을 반환해야 합니다.\n" +
            "KING.compareTo(TWO)의 결과: " + king.compareTo(two));
        
        // TWO < ACE
        assertTrue(two.compareTo(ace) < 0, 
            "compareTo() 오류: TWO가 ACE보다 작은 값을 반환해야 합니다.\n" +
            "TWO.compareTo(ACE)의 결과: " + two.compareTo(ace) + "\n" +
            "음수가 나와야 합니다.");
    }
    
    @Test
    @DisplayName("6. compareTo() 테스트 - 같은 랭크일 때 무늬 비교")
    void testCompareToWithSameRank() {
        // 모두 ACE이지만 무늬가 다른 카드들
        Card aceSpades = new Card(Suit.SPADES, Rank.ACE);
        Card aceHearts = new Card(Suit.HEARTS, Rank.ACE);
        Card aceDiamonds = new Card(Suit.DIAMONDS, Rank.ACE);
        Card aceClubs = new Card(Suit.CLUBS, Rank.ACE);
        
        // Suit enum의 선언 순서: SPADES < HEARTS < DIAMONDS < CLUBS
        assertTrue(aceSpades.compareTo(aceHearts) < 0, 
            "compareTo() 오류: 같은 랭크일 때는 무늬(Suit)로 비교해야 합니다.\n" +
            "스페이드 ACE는 하트 ACE보다 작아야 합니다.\n" +
            "힌트: Rank가 같으면 Suit을 비교하세요.\n" +
            "if (rankCompare != 0) return rankCompare;\n" +
            "return this.suit.compareTo(other.getSuit());");
            
        // 같은 카드끼리 비교
        Card anotherAceSpades = new Card(Suit.SPADES, Rank.ACE);
        assertEquals(0, aceSpades.compareTo(anotherAceSpades), 
            "compareTo() 오류: 완전히 같은 카드는 0을 반환해야 합니다.");
    }
    
    @Test
    @DisplayName("7. equals() 테스트 - 카드 동등성 비교")
    void testEquals() {
        Card card1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card2 = new Card(Suit.HEARTS, Rank.QUEEN);  // card1과 같음
        Card card3 = new Card(Suit.HEARTS, Rank.KING);   // 다른 랭크
        Card card4 = new Card(Suit.SPADES, Rank.QUEEN);  // 다른 무늬
        
        // 같은 카드
        assertEquals(card1, card2, 
            "equals() 오류: 같은 무늬와 랭크를 가진 카드는 true를 반환해야 합니다.\n" +
            "힌트: this.suit.equals(other.suit) && this.rank.equals(other.rank)");
        
        // 다른 카드들
        assertNotEquals(card1, card3, 
            "equals() 오류: 랭크가 다른 카드는 false를 반환해야 합니다.");
            
        assertNotEquals(card1, card4, 
            "equals() 오류: 무늬가 다른 카드는 false를 반환해야 합니다.");
        
        // null과 비교
        assertNotEquals(card1, null, 
            "equals() 오류: null과 비교할 때는 false를 반환해야 합니다.\n" +
            "if (obj == null) return false; 체크가 필요합니다.");
        
        // 다른 타입과 비교
        assertNotEquals(card1, "string", 
            "equals() 오류: 다른 타입과 비교할 때는 false를 반환해야 합니다.\n" +
            "if (!(obj instanceof Card)) return false; 체크가 필요합니다.");
        
        // 자기 자신과 비교
        assertEquals(card1, card1, 
            "equals() 오류: 자기 자신과 비교할 때는 true를 반환해야 합니다.\n" +
            "if (this == obj) return true; 최적화를 추가하세요.");
    }
    
    @Test
    @DisplayName("8. hashCode() 테스트 - equals()와 일관성 확인")
    void testHashCode() {
        Card card1 = new Card(Suit.HEARTS, Rank.QUEEN);
        Card card2 = new Card(Suit.HEARTS, Rank.QUEEN);  // card1과 같음
        Card card3 = new Card(Suit.SPADES, Rank.QUEEN);  // 다름
        
        // equals()가 true이면 hashCode()도 같아야 함
        assertEquals(card1.hashCode(), card2.hashCode(), 
            "hashCode() 오류: equals()가 true인 객체들은 같은 hashCode를 가져야 합니다.\n" +
            "힌트: Objects.hash(suit, rank)를 사용하세요.");
        
        // 다른 카드는 (대부분) 다른 hashCode를 가져야 함
        // 주의: 이것은 반드시 보장되지는 않지만, 좋은 hash 함수는 대부분 다른 값을 반환
        assertNotEquals(card1.hashCode(), card3.hashCode(), 
            "hashCode() 구현을 개선하세요. 다른 카드가 같은 hashCode를 가집니다.\n" +
            "Objects.hash(suit, rank)를 사용하면 좋은 분포를 얻을 수 있습니다.");
    }
    
    @Test
    @DisplayName("9. 실제 게임 시나리오 테스트 - 포커 게임에서의 카드 비교")
    void testRealGameScenario() {
        // 시나리오: 플레이어가 Q를 가지고, 딜러가 J을 가진 경우
        Card playerCard = new Card(Suit.HEARTS, Rank.QUEEN);
        Card dealerCard = new Card(Suit.SPADES, Rank.JACK);
        
        assertTrue(playerCard.compareTo(dealerCard) > 0, 
            "게임 시나리오 오류: 플레이어의 Queen이 딜러의 Jack을 이겨야 합니다.");
        
        // 시나리오: 두 플레이어가 같은 랭크(K)를 가진 경우
        Card player1King = new Card(Suit.HEARTS, Rank.KING);
        Card player2King = new Card(Suit.DIAMONDS, Rank.KING);
        
        assertTrue(player2King.compareTo(player1King) > 0, 
            "게임 시나리오 오류: 같은 King일 때, 다이아몬드가 하트보다 높아야 합니다.\n" +
            "Suit의 enum 순서를 확인하세요: SPADES < HEARTS < DIAMONDS < CLUBS");
    }
}