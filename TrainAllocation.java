import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String Name;
    private int Age;
    private String Login_Id;
    private String Train_Name;
    private int Bogie_No;
    private int Seat_No;

    public User(String Name, int Age, String Login_Id) {
        this.Name = Name;
        this.Age = Age;
        this.Login_Id = Login_Id;
        this.Train_Name = "";
        this.Bogie_No = -1;
        this.Seat_No = -1;
    }

    public String getLogin_Id() {
        return Login_Id;
    }

    public void setTrainInfo(String Train_Name, int Bogie_No, int Seat_No) {
        this.Train_Name = Train_Name;
        this.Bogie_No = Bogie_No;
        this.Seat_No = Seat_No;
    }

    public String getInfo() {
        return "Login Id " + Login_Id + "\nName: " + Name + "\nAge: " + Age + "\nTrain: " +
                (Train_Name.isEmpty() ? "Not Allocated" : Train_Name) + "\nBogie_No :"
                + (Bogie_No == -1 ? "N/A" : Bogie_No) +
                "\nSeat_no :" + (Seat_No == -1 ? "N/A" : Seat_No);

    }

}

class Train {
    private String Name;
    private int[][] Seats;

    public Train(String Name) {
        this.Name = Name;
        Seats = new int[3][2];
    }

    public boolean allocateSeat(User u) {
        for (int i = 0; i < Seats.length; i++) {
            for (int j = 0; j < Seats[i].length; j++) {
                if (Seats[i][j] == 0) {
                    Seats[i][j] = 1;
                    u.setTrainInfo(Name, i + 1, j + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public String getStatus() {
        int booked = 0;
        int TotalSeats = Seats.length * Seats[0].length;
        for (int[] Bogie : Seats) {
            for (int Seats : Bogie) {
                if (Seats == 1) {
                    booked++;
                }
            }
        }
        return "Train: " + Name + "\nTotal Seats: " + TotalSeats +
                "\nBooked Seats: " + booked + "\nRemaining Seats: " + (TotalSeats - booked);
    }
}

class TrainType {
    private List<User> users;
    private Train[] trains;
    private int userCount = 0;

    public TrainType() {
        users = new ArrayList<>();
        trains = new Train[3];
        trains[0] = new Train("Train 1");
        trains[1] = new Train("Train 2");
        trains[2] = new Train("Train 3");
    }

    public String registerUser(String Name, int Age) {
        String Login_Id = "user" + (++userCount);
        users.add(new User(Name, Age, Login_Id));
        return Login_Id;
    }

    public boolean AllocateTrain(String Login_Id, int TrainChoice) {
        for (User u : users) {
            if (u.getLogin_Id().equals(Login_Id)) {
                return trains[TrainChoice - 1].allocateSeat(u);

            }
        }
        return false;
    }

    public String getUserInfo(String Login_Id) {
        for (User u : users) {
            if (u.getLogin_Id().equals(Login_Id)) {
                return u.getInfo();
            }
        }
        return "User Not Found..........";
    }

    public String getAllUsersInfo() {
        StringBuilder sb = new StringBuilder();
        for (User u : users) {
            sb.append(u.getInfo()).append("\n\n");
        }
        return sb.toString();
    }

    public String getTrainStatus() {
        StringBuilder sb = new StringBuilder();
        for (Train train : trains) {
            sb.append(train.getStatus()).append("\n\n");
        }
        return sb.toString();
    }
}

public class TrainAllocation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TrainType trainType = new TrainType();
        int choice;

        do {
            System.out.println("Enter Your Choice: \n" +
                    "1. Enter profile\n" +
                    "2. Enter Train Choice\n" +
                    "3. Display User Information\n" +
                    "4. Display Complete Information\n" +
                    "5. Display Train Status\n" +
                    "6. Exit\n" +
                    "********************************************************");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Your Name:");
                    String Name = sc.nextLine();
                    System.out.println("Ente Your Age");
                    int Age = sc.nextInt();
                    sc.nextLine();
                    String Login_Id = trainType.registerUser(Name, Age);
                    System.out.println("Profile Registered Successfully");
                    System.out.println("Your LoginId is : " + Login_Id);
                    System.out.println("***********************************************");
                    break;
                case 2:
                    System.out.println("Enter Your Login Id: ");
                    String id = sc.nextLine();
                    System.out.println("Select Train:\n1. Train 1\n2. train 2\n3. train 3");
                    int TrainChoice = sc.nextInt();
                    sc.nextLine();
                    if (trainType.AllocateTrain(id, TrainChoice)) {
                        System.out.println("Congratulations!!! Seat allocated Successfully");
                        System.out.println("***********************************************");
                    } else {
                        System.out.println("Sorry...Failed to allocate seat");
                    }
                    break;
                case 3:
                    System.out.println("Enter Your Login Id: ");
                    id = sc.nextLine();
                    System.out.println(trainType.getUserInfo(id));
                    System.out.println("***********************************************");
                    break;
                case 4:
                    System.out.println(trainType.getAllUsersInfo());
                    System.out.println("***********************************************");
                    break;
                case 5:
                    System.out.println(trainType.getTrainStatus());
                    System.out.println("***********************************************");
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.out.println("Thank You!!!");
                    System.out.println("Visit Again");
                    System.out.println("***********************************************");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again...");
            }
        } while (choice != 6);
        sc.close();

    }
}
