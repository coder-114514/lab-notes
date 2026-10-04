import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

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

    //
    public static Student readStudent ( Scanner sc ) {
        System.out.println ( "名字？" );
        String name = sc.nextLine ( );

        System.out.println ( "告诉我他的分数，让我康康他的分数发育正不正常啊" );
        double score = sc.nextDouble ( );
        sc.nextLine ( );

        return new Student ( name , score );
    }

    //录取地址
    public static File dataFile () {
        String home = System.getProperty ( "user.home" );
        return new File ( home , "student.txt" );
    }

    //保存数据
    public static void saveStudents ( ArrayList<Student> students ) {
        try (FileWriter writer = new FileWriter ( dataFile ( ) )) {
            for (Student s : students) {
                writer.write ( s.name + "," + s.score + "\n" );
            }
            System.out.println ( "录入成功" );
        } catch (Exception p) {
            System.out.println ( "录入失败" );
        }
    }

    //读取数据
    public static ArrayList<Student> loadStudents () {
        ArrayList<Student> students = new ArrayList<> ( );
        File file = dataFile ( );
        if (!file.exists ( )) {
            return students;
        }
        try (BufferedReader reader = new BufferedReader ( new FileReader ( file ) )) {
            String line;
            while ((line = reader.readLine ( )) != null) {
                if (line.trim ( ).isEmpty ( )) continue;
                String[] part = line.split ( "," );
                String name = part[0].trim ( );
                double score = Double.parseDouble ( part[1].trim ( ) );
                students.add ( new Student ( name , score ) );
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
    public static void main ( String[] args ) {
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
                    saveStudents ( list );
                } else if (choice == 2) {
                    System.out.println ( "列表大小：" + list.size ( ) );
                    for (Student i : list) {
                        System.out.println ( i );
                    }

                } else if (choice == 3) {
                    Collections.sort ( list , ( a , b ) -> Double.compare ( a.score , b.score ) );
                    System.out.println ( list );
                    saveStudents ( list );
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