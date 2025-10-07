package com.example.servlet;

import com.example.db.UserDB;
import com.example.model.User;
import com.example.util.MailUtilLocal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/emailList")
public class EmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy hành động hiện tại
        String action = request.getParameter("action");
        if (action == null) {
            action = "join"; // hành động mặc định
        }

        // Xử lý hành động
        if (action.equals("insert")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // Kiểm tra dữ liệu đầu vào
            if (firstName == null || lastName == null || email == null ||
                    firstName.trim().isEmpty() || lastName.trim().isEmpty() || email.trim().isEmpty()) {
                request.setAttribute("message", "Vui lòng điền đầy đủ thông tin.");
                request.setAttribute("messageType", "error");
            } else {
                User user = new User(firstName, lastName, email);
                try {
                    // Chèn người dùng vào cơ sở dữ liệu
                    UserDB.insert(user);

                    // Gửi email cho người dùng
                    String to = email;
                    String from = "your-email@gmail.com"; // Thay bằng email của bạn
                    String subject = "Chao mung ban den voi EmailList cua tructruc";
                    String body = "Kinh gui " + firstName + ",\n\n"
                            + "Cam on ban da tham gia EmailList cua chung toi. "
                            + "Chung toi se dam bao gui cho ban cac thong bao ve san pham moi "
                            + "va cac chuong trinh khuyen mai.\n"
                            + "Chuc ban mot ngay tuyet voi va cam on lan nua!\n\n"
                            + "Tran trong,\n"
                            + "Thanh Truc";
                    boolean isBodyHTML = false;

                    MailUtilLocal.sendMail(to, from, subject, body, isBodyHTML);

                    request.setAttribute("message", "Thêm người dùng thành công và email đã được gửi!");
                    request.setAttribute("messageType", "success");
                } catch (Exception e) {
                    request.setAttribute("message", "Không thể xử lý yêu cầu: " + e.getMessage());
                    request.setAttribute("messageType", "error");
                }
            }
        } else {
            request.setAttribute("message", "Hành động không hợp lệ.");
            request.setAttribute("messageType", "error");
        }

        // Chuyển tiếp đến index.jsp
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}