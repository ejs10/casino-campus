package game.components.card;

/**
 * 카드의 무늬를 나타내는 열거형
 * 
 * 이 열거형은 표준 플레잉 카드의 4가지 무늬를 정의합니다.
 * 각 무늬는 고유한 기호와 한글 이름을 가지며, 카드 게임에서 중요한 역할을 합니다.
 * 
 * <p>구현 요구사항:</p>
 * <ul>
 *   <li>각 무늬는 고유한 기호를 가져야 합니다</li>
 *   <li>각 무늬는 한글 이름을 가져야 합니다</li>
 *   <li>getSymbol()은 카드 출력에 사용되는 기호를 반환해야 합니다</li>
 *   <li>toString()은 열거형의 영문 이름을 반환해야 합니다</li>
 * </ul>
 * 
 * <p>무늬 순서 (일반적인 게임에서의 우선순위):</p>
 * <ol>
 *   <li>SPADES (♠) - 스페이드 (검은색 창 모양)</li>
 *   <li>HEARTS (♥) - 하트 (빨간색 하트 모양)</li>
 *   <li>DIAMONDS (♦) - 다이아몬드 (빨간색 다이아몬드 모양)</li>
 *   <li>CLUBS (♣) - 클럽 (검은색 클로버 모양)</li>
 * </ol>
 * 
 * <p>사용 예시:</p>
 * <pre>
 * Suit suit = Suit.HEARTS;
 * System.out.println(suit.getSymbol());     // "♥" 출력
 * System.out.println(suit.getKoreanName()); // "하트" 출력
 * System.out.println(suit);                 // "HEARTS" 출력
 * </pre>
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public enum Suit {
    /**
     * 스페이드 (♠)
     * 
     * <p>검은색 무늬로, 창 모양을 나타냅니다.</p>
     * <p>전통적으로 가장 높은 순위의 무늬로 간주됩니다.</p>
     */
    SPADES("♠", "스페이드"),
    
    /**
     * 하트 (♥)
     * 
     * <p>빨간색 무늬로, 하트 모양을 나타냅니다.</p>
     * <p>사랑과 감정을 상징하는 무늬입니다.</p>
     */
    HEARTS("♥", "하트"),
    
    /**
     * 다이아몬드 (♦)
     * 
     * <p>빨간색 무늬로, 다이아몬드 모양을 나타냅니다.</p>
     * <p>부와 풍요를 상징하는 무늬입니다.</p>
     */
    DIAMONDS("♦", "다이아몬드"),
    
    /**
     * 클럽 (♣)
     * 
     * <p>검은색 무늬로, 클로버 모양을 나타냅니다.</p>
     * <p>전통적으로 가장 낮은 순위의 무늬로 간주됩니다.</p>
     */
    CLUBS("♣", "클럽");
    
    private final String symbol;
    private final String koreanName;
    
    /**
     * Suit 열거형의 생성자
     * 
     * <p>각 무늬는 고유한 기호와 한글 이름을 가집니다.</p>
     * 
     * @param symbol 무늬 기호 (♠, ♥, ♦, ♣)
     * @param koreanName 무늬의 한글 이름
     */
    Suit(String symbol, String koreanName) {
        this.symbol = symbol;
        this.koreanName = koreanName;
    }
    
    /**
     * 무늬의 기호를 반환합니다.
     * 
     * <p>카드를 표시할 때 사용되는 유니코드 기호를 반환합니다.</p>
     * <ul>
     *   <li>SPADES: ♠</li>
     *   <li>HEARTS: ♥</li>
     *   <li>DIAMONDS: ♦</li>
     *   <li>CLUBS: ♣</li>
     * </ul>
     * 
     * @return 무늬 기호 (예: "♠")
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * 무늬의 한글 이름을 반환합니다.
     * 
     * <p>사용자에게 무늬를 한글로 표시할 때 사용됩니다.</p>
     * 
     * @return 무늬의 한글 이름 (예: "스페이드")
     */
    public String getKoreanName() {
        return koreanName;
    }
    
    /**
     * 무늬를 문자열로 표현합니다.
     * 
     * <p>기본적으로 영문 이름을 반환합니다.</p>
     * <p>예: SPADES, HEARTS, DIAMONDS, CLUBS</p>
     * 
     * @return 무늬의 영문 이름 (예: "SPADES")
     */
    @Override
    public String toString() {
        return name();
    }
}