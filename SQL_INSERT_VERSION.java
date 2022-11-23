package SQL_INSERT_VERSION;/*
    한 개 행을 가지고 더미데이터를 만들어주는 프로그램
    
    [입력]
    305	732	1367 1523 5092 118 466 137 1537	4736 3438 1246 5027	1019 3500 173 1474 3952 2321 5398 875 4026 2465 743 1780 1000 668 127
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.StringTokenizer;

public class Main {
    static int[][] data;
    static LocalDate now;
    static double add;
    static int range_date;
    static String table;
    // 날짜를 제외한 필드명을 적는다.
    // 주요기능 필드명 + 버전 필드명을 합쳐서 동시에 INSERT
    // 왜냐하면 주요기능과 버전의 통계 데이터가 같은 테이블에 존재해서
    static String[] field = {"use_neogift", "use_cart_free", "use_alimtalk", "use_powerapp",
            "use_domain", "use_regular_delivery", "use_smart_coupon", "use_market", "order_ver1",
            "order_ver2", "order_unify", "design_pc_d2", "design_pc_d4", "design_mobile_2",
            "design_mobile_4", "design_mobile_pack", "design_mobile_easy", "main_new",
            "main_old", "order_page_pay", "order_page_normal", "security_black", "security_ssl",
            "sns_facebook", "sns_naver", "sns_kakao", "sns_kakaosync", "sns_apple"};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SQL_INSERT_VERSION/input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 400일동안 필드의 data
        table = "stats_version";
        range_date = 400;
        data = new int[range_date][field.length];
        now = LocalDate.now();

        // 제일 최신 날짜의 데이터 입력받기
        for(int i = 0; i < field.length; i++){
            data[0][i] = Integer.parseInt(st.nextToken());
        }

        // 데이터 가공
        for(int i = 0; i < range_date-1; i++){
            for(int j = 0; j < field.length; j++){
                if((add = data[i][j] * 0.005) < 1){
                    data[i + 1][j] = data[i][j] + 1;
                } else {
                    data[i + 1][j] = data[i][j] + (int)add;
                }
            }
        }

        // 날짜 연산 + INSERT 구문 포맷으로 출력
        for(int i = 1; i < range_date-1; i++){
            System.out.println("INSERT INTO `" + table + "` SET `stats_date` = '" + now + "'");
            for(int j = 0; j < field.length; j++){
                System.out.print(", `" + field[j] + "` = '" + data[i][j] + "'");
                System.out.println();
            }
            System.out.println(";\n");

            now = now.minusDays(1);
        }

    }
}
