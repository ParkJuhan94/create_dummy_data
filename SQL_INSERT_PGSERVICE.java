package SQL_INSERT_PGSERVICE;

/*
    한 개 행을 가지고 더미데이터를 만들어주는 프로그램
    
    [입력]
    1176	553	596	77	193	0	643	67	1	1071	756	241	843	984	608	160	46	79	161	289
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
    static String[] field = {"integrate_kcp", "integrate_ksnet", "integrate_lg",
            "integrate_allat", "integrate_allthe", "integrate_ini", "integrate_easy",
            "integrate_nice", "integrate_kt", "mobile_danal", "mobile_mobil",
            "mobile_letter", "simple_payco", "simple_kakao", "simple_samsung",
            "simple_ssg", "simple_settle_bank", "simple_smilepay", "simple_toss",
            "simple_npay"};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SQL_INSERT_PGSERVICE/input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 400일동안 필드의 data
        table = "stats_pgservice";
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
