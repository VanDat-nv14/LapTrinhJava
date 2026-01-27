import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class QuanLySach {
   private static ArrayList<Book> danhSachSach = new ArrayList<>();
   private static Scanner scanner;

   public static void main(String[] var0) {
      int var1;
      do {
         System.out.println("\n--- MENU QUAN LY SACH ---");
         System.out.println("1. Them 1 cuon sach");
         System.out.println("2. Xoa 1 cuon sach");
         System.out.println("3. Thay doi cuon sach");
         System.out.println("4. Xuat thong tin tat ca cac cuon sach");
         System.out.println("5. Tim cuon sach co tua de chua chu 'Lap trinh'");
         System.out.println("6. Lay toi da K cuon sach co gia sach <= P");
         System.out.println("7. Tim sach theo danh sach tac gia");
         System.out.println("0. Thoat");
         System.out.print("Moi ban chon chuc nang: ");
         var1 = scanner.nextInt();
         scanner.nextLine();
         switch (var1) {
            case 0:
               System.out.println("Da thoat chuong trinh.");
               break;
            case 1:
               themSach();
               break;
            case 2:
               xoaSach();
               break;
            case 3:
               suaSach();
               break;
            case 4:
               xuatTatCaSach();
               break;
            case 5:
               timSachLapTrinh();
               break;
            case 6:
               laySachTheoGia();
               break;
            case 7:
               timSachTheoTacGia();
               break;
            default:
               System.out.println("Lua chon khong hop le. Vui long chon lai.");
         }
      } while(var1 != 0);
   }

   private static void themSach() {
      System.out.println("--- Them sach moi ---");
      System.out.print("Nhap ma sach: ");
      int var0 = scanner.nextInt();
      scanner.nextLine();
      Iterator<Book> var1 = danhSachSach.iterator();

      Book var2;
      while(var1.hasNext()) {
         var2 = var1.next();
         if (var2.getMaSach() == var0) {
            System.out.println("Ma sach da ton tai!");
            return;
         }
      }

      System.out.print("Nhap ten sach: ");
      String var6 = scanner.nextLine();
      System.out.print("Nhap ten tac gia: ");
      String var7 = scanner.nextLine();
      System.out.print("Nhap don gia: ");
      double var3 = scanner.nextDouble();
      scanner.nextLine();
      Book var5 = new Book(var0, var6, var7, var3);
      danhSachSach.add(var5);
      System.out.println("Them sach thanh cong!");
   }

   private static void xoaSach() {
      System.out.print("Nhap ma sach muon xoa: ");
      int var0 = scanner.nextInt();
      scanner.nextLine();
      Book var1 = null;
      for (Book var3 : danhSachSach) {
         if (var3.getMaSach() == var0) {
            var1 = var3;
            break;
         }
      }

      if (var1 != null) {
         danhSachSach.remove(var1);
         System.out.println("Da xoa sach co ma " + var0);
      } else {
         System.out.println("Khong tim thay sach co ma " + var0);
      }
   }

   private static void suaSach() {
      System.out.print("Nhap ma sach muon sua: ");
      int var0 = scanner.nextInt();
      scanner.nextLine();
      Book var1 = null;
      for (Book var3 : danhSachSach) {
         if (var3.getMaSach() == var0) {
            var1 = var3;
            break;
         }
      }

      if (var1 != null) {
         System.out.println("Thong tin hien tai: " + var1);
         System.out.print("Nhap ten sach moi (Enter de giu nguyen): ");
         String var6 = scanner.nextLine();
         if (!var6.isEmpty()) {
            var1.setTenSach(var6);
         }

         System.out.print("Nhap tac gia moi (Enter de giu nguyen): ");
         String var7 = scanner.nextLine();
         if (!var7.isEmpty()) {
            var1.setTacGia(var7);
         }

         System.out.print("Nhap don gia moi (-1 de giu nguyen): ");
         double var4 = scanner.nextDouble();
         scanner.nextLine();
         if (var4 != -1.0) {
            var1.setDonGia(var4);
         }

         System.out.println("Cap nhat thanh cong!");
      } else {
         System.out.println("Khong tim thay sach co ma " + var0);
      }
   }

   private static void xuatTatCaSach() {
      if (danhSachSach.isEmpty()) {
         System.out.println("Danh sach trong!");
      } else {
         System.out.println("--- Danh sach sach ---");
         for (Book var1 : danhSachSach) {
            System.out.println(var1);
         }
      }
   }

   private static void timSachLapTrinh() {
      System.out.println("--- Sach chua tu 'Lap trinh' ---");
      boolean var0 = false;
      for (Book var2 : danhSachSach) {

         if (var2.getTenSach().toLowerCase().contains("lap trinh")) {
            System.out.println(var2);
            var0 = true;
         }
      }
      if (!var0) System.out.println("Khong tim thay sach nao.");
   }

   private static void laySachTheoGia() {
      System.out.print("Nhap so luong K: ");
      int var0 = scanner.nextInt();
      System.out.print("Nhap muc gia P: ");
      double var1 = scanner.nextDouble();
      scanner.nextLine();
      System.out.println("--- Top " + var0 + " sach co gia <= " + var1 + " ---");
      int var3 = 0;
      for (Book var5 : danhSachSach) {
         if (var5.getDonGia() <= var1) {
            System.out.println(var5);
            var3++;
            if (var3 >= var0) break;
         }
      }
      if (var3 == 0) System.out.println("Khong co sach nao thoa man.");
   }

   private static void timSachTheoTacGia() {
      System.out.print("Nhap danh sach tac gia (ngan cach boi dau phay): ");
      String var0 = scanner.nextLine();
      String[] var1 = var0.split(",");
      ArrayList<String> var2 = new ArrayList<>();
      for (String var6 : var1) {
         var2.add(var6.trim().toLowerCase());
      }

      System.out.println("--- Sach cua cac tac gia da nhap ---");
      boolean var8 = false;
      for (Book var10 : danhSachSach) {
         for (String var7 : var2) {
            if (var10.getTacGia().trim().equalsIgnoreCase(var7)) {
               System.out.println(var10);
               var8 = true;
               break;
            }
         }
      }
      if (!var8) System.out.println("Khong tim thay sach cua cac tac gia nay.");
   }

   static {
      scanner = new Scanner(System.in);
   }
}