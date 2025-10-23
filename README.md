# Hệ thống Quản lý Chung cư

Ứng dụng web quản lý chung cư được xây dựng bằng Spring Boot 3.5.0, Spring Security 6, PostgreSQL và Thymeleaf.

## 🚀 Công nghệ sử dụng

- **Backend**: Spring Boot 3.5.0
- **Security**: Spring Security 6
- **Database**: PostgreSQL
- **Frontend**: Thymeleaf, HTML5, CSS3, JavaScript
- **Build Tool**: Maven
- **ORM**: Spring Data JPA (Hibernate)

## 📋 Yêu cầu hệ thống

- Java 17 hoặc cao hơn
- Maven 3.6 hoặc cao hơn
- PostgreSQL 12 hoặc cao hơn

## ⚙️ Cài đặt

### 1. Clone repository

```bash
git clone <repository-url>
cd 2025-1\ CNPM
```

### 2. Cấu hình PostgreSQL

Tạo database mới trong PostgreSQL:

```sql
CREATE DATABASE apartment_db;
```

Chạy file SQL để tạo các bảng:

```bash
psql -U postgres -d apartment_db -f database.sql
```

### 3. Cấu hình application

Mở file `src/main/resources/application.yml` và cập nhật thông tin kết nối database:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/apartment_db
    username: postgres
    password: your_password
```

### 4. Build và chạy ứng dụng

```bash
mvn clean install
mvn spring-boot:run
```

Hoặc chạy file jar:

```bash
java -jar target/apartment-management-1.0.0.jar
```

## 🌐 Truy cập ứng dụng

- URL: http://localhost:8080
- Trang đăng nhập: http://localhost:8080/login

## 👤 Tài khoản mặc định

Sau khi chạy SQL script, cần tạo tài khoản admin đầu tiên:

```sql
-- Tạo tài khoản admin (mật khẩu: admin123)
INSERT INTO doi_tuong (cccd, mat_khau, vai_tro, la_cu_dan, ho_va_ten, ngay_sinh, trang_thai_tai_khoan) 
VALUES ('001234567890', '$2a$10$xXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXxXx', 'ban_quan_tri', false, 'Quản trị viên', '1990-01-01', 'hoat_dong');
```

**Lưu ý**: Mật khẩu đã được mã hóa bằng BCrypt. Bạn cần sử dụng BCrypt encoder để tạo mật khẩu mới.

## 📁 Cấu trúc dự án

```
apartment-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/apartment/
│   │   │       ├── controller/         # Controllers
│   │   │       ├── entity/             # Entity classes
│   │   │       ├── repository/         # JPA Repositories
│   │   │       ├── security/           # Security configuration
│   │   │       ├── service/            # Service layer
│   │   │       └── ApartmentManagementApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/               # CSS files
│   │       │   └── js/                # JavaScript files
│   │       ├── templates/             # Thymeleaf templates
│   │       │   ├── admin/
│   │       │   ├── layout/
│   │       │   └── login.html
│   │       └── application.yml
│   └── test/
├── database.sql
├── pom.xml
└── README.md
```

## 🔐 Phân quyền

Hệ thống hỗ trợ các vai trò sau:

1. **Ban quản trị** (`ban_quan_tri`): Quản lý toàn bộ hệ thống
2. **Kế toán** (`ke_toan`): Quản lý hóa đơn, thanh toán
3. **Cơ quan chức năng** (`co_quan_chuc_nang`): Xử lý báo cáo sự cố
4. **Người dùng thường** (`nguoi_dung_thuong`): Cư dân chung cư

## 📊 Chức năng chính

### Quản lý cư dân
- Thêm, sửa, xóa thông tin cư dân
- Tìm kiếm cư dân
- Quản lý trạng thái tài khoản

### Quản lý hộ gia đình
- Quản lý thông tin hộ gia đình
- Gán căn hộ cho hộ gia đình
- Theo dõi thành viên hộ

### Quản lý hóa đơn
- Tạo hóa đơn dịch vụ
- Theo dõi thanh toán
- Thống kê thu chi
- Quản lý công nợ

### Quản lý báo cáo sự cố
- Tiếp nhận báo cáo sự cố
- Phân loại mức độ ưu tiên
- Theo dõi xử lý
- Lưu kết quả xử lý

### Quản lý thông báo
- Gửi thông báo cho cư dân
- Phân loại thông báo
- Quản lý đối tượng nhận

### Dashboard
- Thống kê tổng quan
- Biểu đồ thu chi
- Báo cáo sự cố mới
- Thông báo quan trọng

## 🛠️ Development

### Thêm entity mới

1. Tạo class entity trong `src/main/java/com/apartment/entity/`
2. Tạo repository interface trong `src/main/java/com/apartment/repository/`
3. Tạo service class trong `src/main/java/com/apartment/service/`
4. Tạo controller trong `src/main/java/com/apartment/controller/`
5. Tạo template trong `src/main/resources/templates/`

### Database Migration

Sử dụng file SQL để cập nhật schema:

```bash
psql -U postgres -d apartment_db -f migration.sql
```

## 🧪 Testing

Chạy unit tests:

```bash
mvn test
```

## 📝 Logging

Logs được lưu trong console với các level:
- DEBUG: Chi tiết cho development
- INFO: Thông tin chính
- ERROR: Lỗi hệ thống

Cấu hình trong `application.yml`:

```yaml
logging:
  level:
    com.apartment: DEBUG
    org.springframework.security: DEBUG
```

## 🔒 Security

- Mật khẩu được mã hóa bằng BCrypt
- Session timeout: 30 phút
- CSRF protection enabled
- XSS protection
- Method-level security với `@PreAuthorize`

## 🤝 Đóng góp

1. Fork repository
2. Tạo branch mới (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Tạo Pull Request

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.

## 📧 Liên hệ

Để biết thêm thông tin, vui lòng liên hệ qua email hoặc tạo issue trong repository.

---

**Phát triển bởi**: Nhóm phát triển quản lý chung cư  
**Năm**: 2025  
**Version**: 1.0.0


