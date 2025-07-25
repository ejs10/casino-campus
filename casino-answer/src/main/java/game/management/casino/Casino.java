package game.management.casino;

import game.participants.dealer.Dealer;
import game.participants.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 카지노 메인 클래스
 * 기본 로컬 게임 실행을 담당합니다.
 * 
 * 구현이 필요한 메서드:
 * - main() 메서드: 게임 실행 로직
 * - printFinalResults() 메서드: 최종 결과 출력
 */
public class Casino {
    private static final int INITIAL_MONEY = 10000;
    private static final int PRIZE_AMOUNT = 100;
    private static final int TOTAL_ROUNDS = 100;
    private static final int PLAYER_COUNT = 4;
    
    private static final String[] PLAYER_NAMES = {
        "럭키가이", "포커마스터", "초보자", "불운한자"
    };
    
    public static void main(String[] args) {
        System.out.println("🎰 라스베가스 드림 카지노 🎰");
        System.out.println("════════════════════════════════════════");
        System.out.println("게임을 시작합니다!");
        System.out.println("플레이어 수: " + PLAYER_COUNT + "명");
        System.out.println("총 라운드: " + TOTAL_ROUNDS + "라운드");
        System.out.println("초기 자금: " + INITIAL_MONEY + "원");
        System.out.println("라운드당 상금: " + PRIZE_AMOUNT + "원\n");
        
        // 플레이어 리스트 생성
        List<Player> players = new ArrayList<>();
        
        // 4명의 플레이어 생성
        for (String name : PLAYER_NAMES) {
            players.add(new Player(name, INITIAL_MONEY));
        }
        
        // 딜러 객체 생성
        Dealer dealer = new Dealer();
        
        // 게임 진행
        dealer.playGame(players, TOTAL_ROUNDS);
        
        // 최종 결과 출력
        printFinalResults(players);
    }
    
    /**
     * 최종 결과를 출력합니다.
     * 
     * @param players 플레이어 목록
     */
    private static void printFinalResults(List<? extends Player> players) {
        System.out.println("\n🎰 라스베가스 드림 카지노 - 베타 테스트 결과 🎰");
        System.out.println("════════════════════════════════════════");
        
        // 정렬을 위한 새로운 리스트 생성
        List<Player> sortedPlayers = new ArrayList<>(players);
        
        // 플레이어를 자금 기준으로 내림차순 정렬
        sortedPlayers.sort((p1, p2) -> Integer.compare(p2.getMoney(), p1.getMoney()));
        
        // 메달 배열
        String[] medals = {"🥇", "🥈", "🥉", "😢"};
        
        // 순위별로 결과 출력
        for (int i = 0; i < sortedPlayers.size(); i++) {
            Player player = sortedPlayers.get(i);
            System.out.printf("%s %d위: %s - %,d원 (%d승 %d패 %d무)\n",
                medals[i], 
                i + 1,
                player.getName(),
                player.getMoney(),
                player.getWinCount(),
                player.getLoseCount(),
                player.getDrawCount()
            );
        }
        System.out.println("════════════════════════════════════════");
    }
}