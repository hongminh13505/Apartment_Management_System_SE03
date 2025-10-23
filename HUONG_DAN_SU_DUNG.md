# Hướng dẫn sử dụng hệ thống Quản lý Chung cư

## 📖 Mục lục
1. [Đăng nhập hệ thống](#đăng-nhập-hệ-thống)
2. [Chức năng quản trị viên](#chức-năng-quản-trị-viên)
3. [Quản lý cư dân](#quản-lý-cư-dân)
4. [Quản lý hộ gia đình](#quản-lý-hộ-gia-đình)
5. [Quản lý hóa đơn](#quản-lý-hóa-đơn)
6. [Báo cáo sự cố](#báo-cáo-sự-cố)
7. [Thông báo](#thông-báo)

## 🔐 Đăng nhập hệ thống

1. Truy cập http://localhost:8080/login
2. Nhập số CCCD và mật khẩu
3. Click "Đăng nhập"

**Lưu ý**: 
- Số CCCD là 12 số
- Mật khẩu phân biệt chữ hoa/thường

## 👨‍💼 Chức năng quản trị viên

### Dashboard
- Xem thống kê tổng quan (số cư dân, hộ gia đình, hóa đơn...)
- Xem báo cáo sự cố mới nhất
- Xem thông báo quan trọng

### Phân quyền theo vai trò

| Vai trò | Quyền truy cập |
|---------|---------------|
| Ban quản trị | Toàn bộ hệ thống |
| Kế toán | Quản lý hóa đơn, thanh toán |
| Cơ quan chức năng | Xử lý báo cáo sự cố |
| Người dùng thường | Xem thông tin cá nhân |

## 👥 Quản lý cư dân

### Thêm cư dân mới
1. Vào menu "Quản lý cư dân"
2. Click "Thêm mới"
3. Nhập thông tin:
   - Số CCCD (bắt buộc)
   - Họ và tên (bắt buộc)
   - Ngày sinh (bắt buộc)
   - Giới tính
   - Số điện thoại
   - Email
   - Vai trò (bắt buộc)
   - Mật khẩu (bắt buộc cho tài khoản mới)
4. Chọn "Là cư dân" nếu người này sống tại chung cư
5. Click "Lưu"

### Sửa thông tin cư dân
1. Trong danh sách cư dân, click "Sửa" ở cư dân cần chỉnh sửa
2. Cập nhật thông tin
3. Click "Lưu"

### Xóa cư dân
1. Click "Xóa" ở cư dân cần xóa
2. Xác nhận xóa

**Lưu ý**: Xóa cư dân sẽ xóa toàn bộ dữ liệu liên quan

### Tìm kiếm cư dân
- Nhập tên cư dân vào ô tìm kiếm
- Click "Tìm kiếm"

## 🏠 Quản lý hộ gia đình

### Tạo hộ gia đình mới
1. Vào menu "Hộ gia đình"
2. Click "Thêm mới"
3. Nhập:
   - Mã hộ (VD: HO001)
   - Tên hộ
   - Mã căn hộ (nếu có)
   - Ngày thành lập
4. Click "Lưu"

### Quản lý thành viên hộ
- Thêm thành viên vào hộ gia đình
- Chỉ định chủ hộ
- Theo dõi quan hệ với chủ hộ
- Ghi nhận ngày bắt đầu/kết thúc

## 💰 Quản lý hóa đơn

### Tạo hóa đơn mới
1. Vào menu "Hóa đơn"
2. Click "Tạo hóa đơn"
3. Nhập:
   - Chọn hộ gia đình
   - Loại hóa đơn (Dịch vụ, Sửa chữa, Phạt...)
   - Số tiền
   - Hạn thanh toán
   - Dịch vụ liên quan (nếu có)
4. Click "Lưu"

### Thanh toán hóa đơn
1. Trong danh sách hóa đơn chưa thanh toán
2. Click "Thanh toán"
3. Nhập phương thức thanh toán (Tiền mặt/Chuyển khoản)
4. Xác nhận

### Lọc hóa đơn
- Tất cả
- Chưa thanh toán
- Đã thanh toán
- Quá hạn

### Thống kê
- Tổng đã thu
- Tổng chưa thu
- Công nợ còn lại

## ⚠️ Báo cáo sự cố

### Tạo báo cáo sự cố
1. Vào menu "Báo cáo sự cố"
2. Click "Tạo báo cáo"
3. Nhập:
   - Chọn người báo cáo (cư dân)
   - Tiêu đề
   - Mô tả chi tiết sự cố
   - Vị trí xảy ra sự cố
   - Mức độ ưu tiên (Thấp/Bình thường/Cao/Khẩn cấp)
   - Phương thức báo cáo
4. Click "Lưu"

### Xử lý báo cáo
1. Chọn báo cáo cần xử lý
2. Click "Xử lý"
3. Nhập kết quả xử lý
4. Hệ thống tự động:
   - Cập nhật trạng thái "Hoàn thành"
   - Ghi nhận người xử lý
   - Ghi nhận thời gian hoàn thành

### Lọc báo cáo theo trạng thái
- Tất cả
- Mới tiếp nhận
- Đang xử lý
- Hoàn thành

### Mức độ ưu tiên
| Mức độ | Mô tả | Màu hiển thị |
|--------|-------|--------------|
| Thấp | Không gấp | Xanh dương |
| Bình thường | Mức độ thông thường | Xanh lá |
| Cao | Cần ưu tiên | Vàng |
| Khẩn cấp | Cần xử lý ngay | Đỏ |

## 📢 Thông báo

### Tạo thông báo mới
1. Vào menu "Thông báo"
2. Click "Tạo thông báo"
3. Nhập:
   - Tiêu đề
   - Nội dung thông báo
   - Loại thông báo (Quan trọng/Bình thường/Khẩn cấp)
   - Đối tượng nhận:
     * Tất cả cư dân
     * Chủ hộ
     * Theo hộ cụ thể
4. Click "Lưu"

### Quản lý thông báo
- Ẩn/Hiện thông báo
- Sửa nội dung
- Xóa thông báo

## 🔍 Tìm kiếm và lọc dữ liệu

### Tìm kiếm nhanh
- Sử dụng ô tìm kiếm ở đầu mỗi trang danh sách
- Nhập từ khóa và Enter

### Lọc theo điều kiện
- Sử dụng các nút lọc (Tất cả, Đã thanh toán, Chưa thanh toán...)
- Chọn điều kiện phù hợp

## 📊 Báo cáo và thống kê

### Dashboard
- Số lượng cư dân
- Số hộ gia đình
- Tổng thu nhập
- Công nợ còn lại
- Số báo cáo sự cố mới

### Xuất báo cáo
- Sử dụng chức năng Print (Ctrl+P)
- Xuất Excel (nếu có)

## 🔒 Bảo mật

### Thay đổi mật khẩu
1. Liên hệ quản trị viên
2. Quản trị viên sẽ cập nhật mật khẩu mới

### Đăng xuất
- Click nút "Đăng xuất" ở góc trên bên phải

## ❓ Câu hỏi thường gặp

### 1. Quên mật khẩu?
Liên hệ quản trị viên để reset mật khẩu.

### 2. Không thể đăng nhập?
- Kiểm tra số CCCD đã đúng chưa
- Kiểm tra tài khoản có bị khóa không
- Liên hệ quản trị viên

### 3. Lỗi khi lưu dữ liệu?
- Kiểm tra kết nối mạng
- Kiểm tra các trường bắt buộc đã điền đủ
- Refresh trang và thử lại

### 4. Không thấy menu?
- Kiểm tra quyền truy cập của tài khoản
- Liên hệ quản trị viên để cấp quyền

## 📞 Hỗ trợ

Nếu gặp vấn đề trong quá trình sử dụng:
- Liên hệ quản trị viên hệ thống
- Email: support@apartment.com
- Hotline: 1900-xxxx

---

**Chúc bạn sử dụng hệ thống hiệu quả!**


