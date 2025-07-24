package game.components.card;

/**
 * 카드 한 장을 나타내는 클래스
 * 
 * 이 클래스는 카드 게임에서 사용되는 카드 한 장의 기본 동작을 정의합니다.
 * 모든 카드는 무늬(Suit)와 랭크(Rank)를 가지며, 불변(immutable) 객체로 구현되어야 합니다.
 * 
 * <p>구현 요구사항:</p>
 * <ul>
 *   <li>카드는 생성 후 상태가 변경되지 않아야 합니다 (불변 객체)</li>
 *   <li>같은 무늬와 랭크를 가진 카드는 동일한 것으로 간주되어야 합니다</li>
 *   <li>equals()와 hashCode()는 일관성 있게 구현되어야 합니다</li>
 *   <li>toString()은 사람이 읽기 쉬운 형태로 카드를 표현해야 합니다</li>
 * </ul>
 * 
 * <p>사용 예시:</p>
 * <pre>
 * Card card = new Card(Suit.HEARTS, Rank.ACE);
 * System.out.println(card);  // "A♥" 출력
 * System.out.println(card.getValue());  // 14 출력
 * </pre>
 * 
 * 구현이 필요한 메서드:
 * - getValue(): 카드의 포커 순위 값 반환
 * - compareTo(Card other): 카드 비교
 * - toString(): 카드 문자열 표현
 * - equals(Object obj): 동등성 비교
 * - hashCode(): 해시 코드 생성
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;
    
    /**
     * Card 생성자
     * @param suit 카드의 무늬
     * @param rank 카드의 숫자/문자
     */
    public Card(Suit suit, Rank rank) {
        if (suit == null || rank == null) {
            throw new IllegalArgumentException("Suit와 Rank는 null일 수 없습니다.");
        }
        this.suit = suit;
        this.rank = rank;
    }
    
    /**
     * 카드의 무늬를 반환합니다.
     * 
     * @return 카드의 무늬 (SPADES, HEARTS, DIAMONDS, CLUBS 중 하나)
     */
    public Suit getSuit() {
        return this.suit;
    }
    
    /**
     * 카드의 랭크를 반환합니다.
     * 
     * @return 카드의 랭크 (TWO부터 ACE까지)
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * 카드의 포커 게임에서의 순위 값을 반환합니다.
     * 
     * <p>포커에서 카드 강도를 비교하기 위한 숫자 값:</p>
     * <ul>
     *   <li>TWO (2): 2 (가장 약한 카드)</li>
     *   <li>THREE (3): 3</li>
     *   <li>FOUR (4): 4</li>
     *   <li>FIVE (5): 5</li>
     *   <li>SIX (6): 6</li>
     *   <li>SEVEN (7): 7</li>
     *   <li>EIGHT (8): 8</li>
     *   <li>NINE (9): 9</li>
     *   <li>TEN (10): 10</li>
     *   <li>JACK (J): 11</li>
     *   <li>QUEEN (Q): 12</li>
     *   <li>KING (K): 13</li>
     *   <li>ACE (A): 14 (가장 강한 카드)</li>
     * </ul>
     * 
     * <p>이 값은 카드를 비교할 때 사용됩니다. 
     * 예: ACE(14) > KING(13) > QUEEN(12) > ... > TWO(2)</p>
     * 
     * @return 카드의 포커 순위 값 (2-14)
     */
    public int getValue() {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. 이 카드가 가진 rank 필드를 활용하세요
        // 2. Rank enum을 살펴보면 getValue() 메서드가 있습니다
        // 3. 이 메서드에서는 해당 메서드를 호출하여 값을 반환하면 됩니다
        // 
        // 테스트 실패 시 확인사항:
        // - "getValue()는 2를 반환해야 합니다" 에러: rank의 getValue() 메서드를 호출하지 않았습니다
        // - NullPointerException 에러: rank 필드를 사용하지 않고 다른 것을 사용했습니다

        return this.rank.getValue();
    }

    @Override
    public int compareTo(Card other) {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. 먼저 두 카드의 Rank를 비교하세요
        //    - this.rank와 other의 rank를 비교합니다
        //    - Enum은 compareTo 메서드를 제공합니다
        // 2. Rank가 같다면 Suit을 비교하세요
        //    - 첫 번째 비교 결과가 0이 아니면 바로 반환
        //    - 0이면 suit을 비교하여 반환
        // 
        // 테스트 실패 시 확인사항:
        // - "ACE가 KING보다 큰 값을 반환해야 합니다" 에러: Rank 비교가 잘못되었습니다
        // - "스페이드 ACE는 하트 ACE보다 작아야 합니다" 에러: 같은 Rank일 때 Suit 비교를 하지 않았습니다
        // - "완전히 같은 카드는 0을 반환해야 합니다" 에러: 같은 카드 처리가 잘못되었습니다

        if(this.rank.compareTo(other.getRank())==0 ){
            return this.suit.compareTo(other.getSuit());
        }
        return this.rank.compareTo(other.getRank());
    }
    
    /**
     * 카드를 문자열로 표현합니다.
     * 
     * <p>표현 형식:</p>
     * <ul>
     *   <li>랭크 심볼 + 무늬 기호</li>
     *   <li>예: "A♠", "10♥", "K♦", "2♣"</li>
     * </ul>
     * 
     * @return 카드의 문자열 표현
     */
    @Override
    public String toString() {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. 이 카드의 rank와 suit 필드를 활용하세요
        // 2. Rank enum과 Suit enum을 살펴보면 getSymbol() 메서드가 있습니다
        // 3. 두 심볼을 적절히 조합하여 문자열을 만드세요
        // 4. 순서는 랭크 심볼이 먼저, 무늬 기호가 나중입니다
        // 
        // 테스트 실패 시 확인사항:
        // - "A와 ♠가 포함되어야 함" 에러: 심볼을 올바르게 가져오지 못했거나 순서가 잘못되었습니다
        // - "10과 ♥가 포함되어야 함" 에러: 두 자리 숫자도 올바르게 처리해야 합니다

        return rank.getSymbol() + suit.getSymbol() ;
    }
    
    /**
     * 다른 객체와 이 카드가 같은지 비교합니다.
     * 
     * <p>두 카드가 같으려면:</p>
     * <ul>
     *   <li>같은 무늬(Suit)를 가져야 합니다</li>
     *   <li>같은 랭크(Rank)를 가져야 합니다</li>
     * </ul>
     * 
     * @param obj 비교할 객체
     * @return 같은 무늬와 랭크를 가지면 true, 그렇지 않으면 false
     */
    @Override
    public boolean equals(Object obj) {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. null 체크: 입력이 null이면 false 반환
        // 2. 자기 자신과의 비교: this == obj이면 true 반환 (성능 최적화)
        // 3. 타입 체크: obj가 Card 타입인지 확인 (instanceof 사용)
        // 4. 타입 변환: obj를 Card로 캐스팅
        // 5. 필드 비교: suit과 rank가 모두 같은지 확인
        // 
        // 테스트 실패 시 확인사항:
        // - "null과 비교할 때는 false를 반환해야 합니다" 에러: null 체크를 하지 않았습니다
        // - "다른 타입과 비교할 때는 false를 반환해야 합니다" 에러: instanceof 체크를 하지 않았습니다
        // - "같은 무늬와 랭크를 가진 카드는 true를 반환해야 합니다" 에러: 필드 비교가 잘못되었습니다
        
        throw new UnsupportedOperationException("equals() 메서드가 아직 구현되지 않았습니다");
    }
    
    /**
     * 카드의 해시 코드를 반환합니다.
     * 
     * <p>해시 코드는 equals()와 일관성을 유지해야 합니다:</p>
     * <ul>
     *   <li>두 카드가 equals()로 같다면, hashCode()도 같아야 합니다</li>
     *   <li>무늬와 랭크를 기반으로 해시 코드를 생성해야 합니다</li>
     * </ul>
     * 
     * @return 카드의 해시 코드
     */
    @Override
    public int hashCode() {
        // TODO: 구현하세요
        // 
        // 구현 힌트:
        // 1. suit과 rank 필드를 모두 사용해야 합니다
        // 2. java.util.Objects 클래스의 hash() 메서드를 활용하면 편리합니다
        // 3. 또는 직접 계산할 수도 있습니다 (예: 31 * suit.hashCode() + rank.hashCode())
        // 
        // 테스트 실패 시 확인사항:
        // - "equals()가 true인 객체들은 같은 hashCode를 가져야 합니다" 에러: 
        //   equals()에서 사용한 필드와 동일한 필드를 사용하지 않았습니다
        
        throw new UnsupportedOperationException("hashCode() 메서드가 아직 구현되지 않았습니다");
    }
}