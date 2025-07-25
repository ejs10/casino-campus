package game.components.card;

/**
 * 카드의 랭크(숫자/문자)를 나타내는 열거형
 * 
 * 이 열거형은 표준 플레잉 카드의 13가지 랭크를 정의합니다.
 * 각 랭크는 고유한 값과 심볼을 가지며, 포커 게임에서 카드의 강도를 결정합니다.
 * 
 * <p>구현 요구사항:</p>
 * <ul>
 *   <li>각 랭크는 고유한 숫자 값을 가져야 합니다 (2-14)</li>
 *   <li>각 랭크는 표시용 심볼을 가져야 합니다</li>
 *   <li>getValue()는 포커 게임에서의 순위 값을 반환해야 합니다</li>
 *   <li>getSymbol()은 카드 출력에 사용되는 심볼을 반환해야 합니다</li>
 * </ul>
 * 
 * <p>랭크 순서 (낮은 것부터 높은 것까지):</p>
 * <ol>
 *   <li>TWO (2) - 가장 낮은 카드</li>
 *   <li>THREE (3)</li>
 *   <li>FOUR (4)</li>
 *   <li>FIVE (5)</li>
 *   <li>SIX (6)</li>
 *   <li>SEVEN (7)</li>
 *   <li>EIGHT (8)</li>
 *   <li>NINE (9)</li>
 *   <li>TEN (10)</li>
 *   <li>JACK (11) - J</li>
 *   <li>QUEEN (12) - Q</li>
 *   <li>KING (13) - K</li>
 *   <li>ACE (14) - A, 가장 높은 카드</li>
 * </ol>
 * 
 * <p>사용 예시:</p>
 * <pre>
 * Rank rank = Rank.ACE;
 * System.out.println(rank.getValue());  // 14 출력
 * System.out.println(rank.getSymbol()); // "A" 출력
 * System.out.println(rank);             // "ACE" 출력
 * </pre>
 * 
 * <p>참고: 일부 게임에서는 ACE를 1로 사용하기도 합니다 (예: 백스트레이트).</p>
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public enum Rank {
    /**
     * 2 - 가장 낮은 랭크
     * 
     * <p>포커에서 가장 약한 카드로 간주됩니다.</p>
     */
    TWO(2, "2"),
    
    /**
     * 3
     * 
     * <p>두 번째로 낮은 랭크의 카드입니다.</p>
     */
    THREE(3, "3"),
    
    /**
     * 4
     * 
     * <p>세 번째로 낮은 랭크의 카드입니다.</p>
     */
    FOUR(4, "4"),
    
    /**
     * 5
     * 
     * <p>네 번째로 낮은 랭크의 카드입니다.</p>
     */
    FIVE(5, "5"),
    
    /**
     * 6
     * 
     * <p>중간보다 낮은 랭크의 카드입니다.</p>
     */
    SIX(6, "6"),
    
    /**
     * 7
     * 
     * <p>중간 랭크의 카드입니다.</p>
     */
    SEVEN(7, "7"),
    
    /**
     * 8
     * 
     * <p>중간 랭크의 카드입니다.</p>
     */
    EIGHT(8, "8"),
    
    /**
     * 9
     * 
     * <p>중간보다 높은 랭크의 카드입니다.</p>
     */
    NINE(9, "9"),
    
    /**
     * 10
     * 
     * <p>숫자 카드 중 가장 높은 랭크의 카드입니다.</p>
     */
    TEN(10, "10"),
    
    /**
     * Jack - 잭 (J), 값은 11
     * 
     * <p>인물 카드 중 가장 낮은 랭크입니다.</p>
     * <p>군사 또는 하인을 나타냅니다.</p>
     */
    JACK(11, "J"),
    
    /**
     * Queen - 퀸 (Q), 값은 12
     * 
     * <p>인물 카드 중 두 번째 랭크입니다.</p>
     * <p>여왕을 나타냅니다.</p>
     */
    QUEEN(12, "Q"),
    
    /**
     * King - 킹 (K), 값은 13
     * 
     * <p>인물 카드 중 세 번째 랭크입니다.</p>
     * <p>왕을 나타냅니다.</p>
     */
    KING(13, "K"),
    
    /**
     * Ace - 에이스 (A), 값은 14 (가장 높은 카드)
     * 
     * <p>포커에서 가장 강한 카드로 간주됩니다.</p>
     * <p>일부 게임에서는 1로도 사용될 수 있습니다.</p>
     */
    ACE(14, "A");
    
    private final int value;
    private final String symbol;
    
    /**
     * Rank 열거형의 생성자
     * 
     * <p>각 랭크는 포커 게임에서의 순위 값과 표시용 심볼을 가집니다.</p>
     * 
     * @param value 랭크의 숫자 값 (2-14)
     * @param symbol 랭크의 심볼 (2-10, J, Q, K, A)
     */
    Rank(int value, String symbol) {
        this.value = value;
        this.symbol = symbol;
    }
    
    /**
     * 랭크의 숫자 값을 반환합니다.
     * 
     * <p>포커 게임에서 카드의 강도를 비교하는 데 사용됩니다.</p>
     * <ul>
     *   <li>TWO: 2 (가장 약함)</li>
     *   <li>THREE: 3</li>
     *   <li>FOUR: 4</li>
     *   <li>FIVE: 5</li>
     *   <li>SIX: 6</li>
     *   <li>SEVEN: 7</li>
     *   <li>EIGHT: 8</li>
     *   <li>NINE: 9</li>
     *   <li>TEN: 10</li>
     *   <li>JACK: 11</li>
     *   <li>QUEEN: 12</li>
     *   <li>KING: 13</li>
     *   <li>ACE: 14 (가장 강함)</li>
     * </ul>
     * 
     * @return 랭크의 숫자 값 (2-14)
     */
    public int getValue() {
        return value;
    }
    
    /**
     * 랭크의 심볼을 반환합니다.
     * 
     * <p>카드를 표시할 때 사용되는 문자 또는 숫자를 반환합니다.</p>
     * <ul>
     *   <li>숫자 카드: "2" ~ "10"</li>
     *   <li>인물 카드: "J", "Q", "K"</li>
     *   <li>에이스: "A"</li>
     * </ul>
     * 
     * @return 랭크의 심볼 (예: "A", "K", "10")
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * 랭크를 문자열로 표현합니다.
     * 
     * <p>기본적으로 영문 이름을 반환합니다.</p>
     * <p>예: TWO, THREE, FOUR, ..., JACK, QUEEN, KING, ACE</p>
     * 
     * @return 랭크의 영문 이름 (예: "ACE", "KING")
     */
    @Override
    public String toString() {
        return name();
    }
}