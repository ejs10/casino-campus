package game.participants.player;

import game.components.hand.Hand;

/**
 * 플레이어의 기본 동작을 정의하는 클래스
 * 
 * 구현이 필요한 메서드:
 * - addMoney() 메서드: 돈 추가 (음수 체크)
 * - removeMoney() 메서드: 돈 차감 (잔액 체크)
 * 
 * @author XIYO
 * @version 1.0
 * @since 2024-01-01
 */
public class Player {
    private String name;
    private int money;
    private Hand hand;
    private int winCount;
    private int loseCount;
    private int drawCount;
    
    /**
     * Player 생성자
     * @param name 플레이어 이름
     * @param initialMoney 초기 자금
     */
    public Player(String name, int initialMoney) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
        }
        if (initialMoney < 0) {
            throw new IllegalArgumentException("초기 자금은 음수일 수 없습니다.");
        }
        
        this.name = name;
        this.money = initialMoney;
        this.hand = new Hand();
        this.winCount = 0;
        this.loseCount = 0;
        this.drawCount = 0;
    }
    
    /**
     * 플레이어의 이름을 반환합니다.
     * 
     * @return 플레이어의 이름
     */
    public String getName() {
        return name;
    }
    
    /**
     * 플레이어의 현재 자금을 반환합니다.
     * 
     * @return 현재 보유 자금
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * 플레이어에게 돈을 추가합니다.
     * 
     * @param amount 추가할 금액
     */
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 음수일 수 없습니다.");
        }
        money += amount;
    }
    
    /**
     * 플레이어로부터 돈을 차감합니다.
     * 
     * @param amount 차감할 금액
     * @return 차감 성공 여부 (잔액 부족시 false)
     */
    public boolean removeMoney(int amount) {
        if (amount < 0) {
            return false;
        }
        if (money >= amount) {
            money -= amount;
            return true;
        }
        return false;
    }
    
    /**
     * 플레이어의 현재 핸드를 반환합니다.
     * 
     * @return 플레이어의 핸드
     */
    public Hand getHand() {
        return hand;
    }
    
    /**
     * 플레이어의 핸드를 설정합니다.
     * 
     * @param hand 설정할 핸드
     */
    public void setHand(Hand hand) {
        if (hand == null) {
            throw new IllegalArgumentException("핸드는 null일 수 없습니다.");
        }
        this.hand = hand;
    }
    
    /**
     * 플레이어의 승리 횟수를 반환합니다.
     * 
     * @return 승리 횟수
     */
    public int getWinCount() {
        return winCount;
    }
    
    /**
     * 플레이어의 패배 횟수를 반환합니다.
     * 
     * @return 패배 횟수
     */
    public int getLoseCount() {
        return loseCount;
    }
    
    /**
     * 플레이어의 무승부 횟수를 반환합니다.
     * 
     * @return 무승부 횟수
     */
    public int getDrawCount() {
        return drawCount;
    }
    
    /**
     * 승리를 기록합니다.
     */
    public void recordWin() {
        winCount++;
    }
    
    /**
     * 패배를 기록합니다.
     */
    public void recordLose() {
        loseCount++;
    }
    
    /**
     * 무승부를 기록합니다.
     */
    public void recordDraw() {
        drawCount++;
    }
    
    @Override
    public String toString() {
        return String.format("%s (자금: %d원, 전적: %d승 %d패 %d무)", 
            name, money, winCount, loseCount, drawCount);
    }
}