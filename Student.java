import java.util.ArrayList;
import java.util.Scanner;
import java.sql.* ;

//声明//
public class Student {
    String name;
    double score;

    //构造函数   作用还需理解
    public Student ( String name , double score ) {
        this.name = name;
        this.score = score;
    }

    //普通方法，这个类能干啥//
    public void introduce () {
        System.out.println ( "名字" + name + "，分数" + score + "录入成功" );
    }

    //录入方法
    public static Student readStudent ( Scanner sc ) {
        System.out.println ( "名字？" );
        String name = sc.nextLine ( );

        System.out.println ( "告诉我他的分数，让我康康他的发育正不正常啊" );
        double score = sc.nextDouble ( );
        sc.nextLine ( );

        return new Student ( name , score );
    }

    //原录取地址，水字数用
//    public static File dataFile () {
//        String home = System.getProperty ( "user.home" );
//        return new File ( home , "student.txt" );
//    }

    //唯一一处链接数据库的地方
    private static Connection getConnection () throws Exception {
        String url = "jdbc:mysql://localhost:3306/lab?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai" ;
        return DriverManager.getConnection (url,"root","lab123456" ) ;
    }
    //保存数据
    public static void saveStudents ( ArrayList<Student> students ) {
        try (Connection conn = getConnection ();
             PreparedStatement dl = conn.prepareStatement ("DELETE FROM student");
             PreparedStatement ps = conn.prepareStatement ("INSERT INTO student (name,score) VALUE (?,?) ")) {
            dl.executeUpdate();
            for (Student s : students) {
            ps.setString(1,s.name);
            ps.setDouble(2,s.score);
            ps.executeUpdate();
            }


        } catch (Exception p) {
            System.out.println ( "录入失败" );
        }
    }

    //读取数据
    public static ArrayList<Student> loadStudents () {
        ArrayList<Student> students = new ArrayList<> ( );

        try (Connection conn =getConnection ();
        ResultSet rs = conn.prepareStatement("SELECT name,score FROM student ").executeQuery()){
            while (rs.next ()) {
                students.add(  new Student( rs.getString("name") ,rs.getDouble("score" ) ));
            }
            System.out.println ( "读取到" + students.size ( ) + "个学生" );
        } catch (Exception l) {
            System.out.println ( "读取失败" );
        }
        return students;
    }

    //重写 toString()
    @Override
    public String toString () {
        return "姓名 " + name + " ，分数 " + score;
    }

    //程序入口//
    public static void main ( String[] args )  {
        ArrayList<Student> list = loadStudents ( );
        Scanner sc = new Scanner ( System.in );


        while (true) {
            System.out.println ( "这是菜单，输入数字即可执行操作" );
            System.out.println ( "1.录 成绩" );
            System.out.println ( "2.看别人成绩" );
            System.out.println ( "3.把所有成绩排序" );
            System.out.println ( "4.退出" );


            try {
                int choice = sc.nextInt ( );

                if (choice == 1) {
                    System.out.println ( "你想录几次？" );
                    int t = sc.nextInt ( );
                    sc.nextLine ( );
                    for (int i = 1; i <= t; ) {
                        try {
                            System.out.println ( "第" + i + "次输入" );
                            Student o = readStudent ( sc );
                            Student s = new Student ( o.name , o.score );
                            s.introduce ( );
                            list.add ( s );
                            i++;
                        } catch (Exception e) {
                            System.out.println ( "我超威，给我数字输好了啊" );
                            sc.nextLine ( );
                        }
                    }
                    System.out.println ("录入成功") ;
                    saveStudents ( list ) ; 

                } else if (choice == 2) {
                    System.out.println ( "列表大小：" + list.size ( ) );
                    for (Student i : list) {
                        System.out.println ( i );
                    }

                } else if (choice == 3) {
                    Connection conn = getConnection ();
                    ResultSet rs = conn.prepareStatement("SELECT name,score FROM student ORDER BY score DESC ").executeQuery() ;
                    while(rs.next()) {
                        System.out.println("姓名" + rs.getString("name") + "  分数" + rs.getDouble("score")) ;
                    }
                    saveStudents ( list );
                    conn.close();
                } else if (choice == 4) {
                    System.exit ( 0 );
                } else {
                    System.out.println ( "请输入正确数字" );

                }
            } catch (Exception q) {
                {
                    System.out.println ( "请输入正确数字" );
                    sc.nextLine();}
            }
        }
    }
}