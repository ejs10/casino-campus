import game.management.casino.Casino;

/**
 * 카지노 게임의 메인 엔트리 포인트
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("     🎰 라스베가스 드림 카지노 🎰      ");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
        System.out.println("로컬 게임을 시작합니다.");
        System.out.println("4명의 플레이어와 100라운드를 진행합니다.\n");
        
        // Casino 클래스의 main 메소드 호출
        Casino.main(new String[]{});
    }
}