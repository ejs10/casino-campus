package game.management.casino;

/**
 * 게임의 현재 상태를 나타내는 열거형
 */
public enum GameState {
    /**
     * 게임이 시작되지 않은 상태
     */
    NOT_STARTED("게임 시작 전"),
    
    /**
     * 플레이어 연결을 기다리는 중
     */
    WAITING_FOR_PLAYERS("플레이어 대기 중"),
    
    /**
     * 게임이 진행 중인 상태
     */
    IN_PROGRESS("게임 진행 중"),
    
    /**
     * 게임이 일시 정지된 상태
     */
    PAUSED("일시 정지"),
    
    /**
     * 게임이 종료된 상태
     */
    ENDED("게임 종료");
    
    private final String displayName;
    
    GameState(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}