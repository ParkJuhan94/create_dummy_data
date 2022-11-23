package SQL_INSERT_ADDSERVICE;

/*
    한 개 행을 가지고 더미데이터를 만들어주는 프로그램
    
    [입력]
    6784 1103 188 289 221 4	3 284 25
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
    static String[] field = {"stats_imgbank", "stats_beebank", "stats_powerpack",
            "stats_powerapp", "stats_080", "stats_acecounter",
            "stats_acecounter_mobile", "stats_analyans", "stats_vfinder"};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SQL_INSERT_ADDSERVICE/input.txt"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 400일동안 필드의 data
        table = "stats_addservice";
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
