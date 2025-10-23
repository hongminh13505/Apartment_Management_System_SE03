-- File dữ liệu test cho hệ thống quản lý chung cư
-- Chạy file này sau khi đã import database.sql

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM phan_hoi;
DELETE FROM thong_bao_ho;
DELETE FROM thong_bao;
DELETE FROM hoa_don;
DELETE FROM anh_bao_cao_su_co;
DELETE FROM bao_cao_su_co;
DELETE FROM dang_ky_dich_vu;
DELETE FROM dich_vu;
DELETE FROM tai_san_chung_cu;
DELETE FROM thanh_vien_ho;
DELETE FROM ho_gia_dinh;
DELETE FROM doi_tuong;

-- Reset sequence
ALTER SEQUENCE doi_tuong_id_seq RESTART WITH 1;
ALTER SEQUENCE ho_gia_dinh_id_seq RESTART WITH 1;
ALTER SEQUENCE thanh_vien_ho_id_seq RESTART WITH 1;
ALTER SEQUENCE tai_san_chung_cu_id_seq RESTART WITH 1;
ALTER SEQUENCE dich_vu_id_seq RESTART WITH 1;
ALTER SEQUENCE dang_ky_dich_vu_id_seq RESTART WITH 1;
ALTER SEQUENCE bao_cao_su_co_id_seq RESTART WITH 1;
ALTER SEQUENCE anh_bao_cao_su_co_id_seq RESTART WITH 1;
ALTER SEQUENCE hoa_don_id_seq RESTART WITH 1;
ALTER SEQUENCE thong_bao_id_seq RESTART WITH 1;
ALTER SEQUENCE thong_bao_ho_id_seq RESTART WITH 1;
ALTER SEQUENCE phan_hoi_id_seq RESTART WITH 1;

-- Thêm dữ liệu đối tượng (người dùng)
INSERT INTO doi_tuong (cccd, mat_khau, vai_tro, la_cu_dan, ho_va_ten, ngay_sinh, gioi_tinh, so_dien_thoai, email, trang_thai_tai_khoan, que_quan, lan_dang_nhap_cuoi) VALUES
-- Admin (đã có)
('001234567890', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ban_quan_tri', false, 'Nguyễn Văn Admin', '1990-01-01', 'nam', '0123456789', 'admin@chungcu.com', 'hoat_dong', 'Hà Nội', NOW()),

-- Cư dân
('001234567891', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'nguoi_dung_thuong', true, 'Trần Thị Mai', '1985-05-15', 'nu', '0987654321', 'mai.tran@email.com', 'hoat_dong', 'TP.HCM', NOW()),
('001234567892', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'nguoi_dung_thuong', true, 'Lê Văn Nam', '1992-03-20', 'nam', '0912345678', 'nam.le@email.com', 'hoat_dong', 'Đà Nẵng', NOW()),
('001234567893', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'nguoi_dung_thuong', true, 'Phạm Thị Hoa', '1988-12-10', 'nu', '0923456789', 'hoa.pham@email.com', 'hoat_dong', 'Hải Phòng', NOW()),
('001234567894', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'nguoi_dung_thuong', true, 'Hoàng Văn Minh', '1995-07-25', 'nam', '0934567890', 'minh.hoang@email.com', 'hoat_dong', 'Cần Thơ', NOW()),

-- Kế toán
('001234567895', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ke_toan', false, 'Vũ Thị Lan', '1987-09-12', 'nu', '0945678901', 'lan.vu@email.com', 'hoat_dong', 'Hà Nội', NOW()),

-- Cơ quan chức năng
('001234567896', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'co_quan_chuc_nang', false, 'Đặng Văn Tuấn', '1983-11-08', 'nam', '0956789012', 'tuan.dang@email.com', 'hoat_dong', 'TP.HCM', NOW());

-- Thêm dữ liệu hộ gia đình
INSERT INTO ho_gia_dinh (ma_ho, ten_ho, ma_can_ho, ngay_thanh_lap, trang_thai) VALUES
('HO001', 'Hộ gia đình Trần Thị Mai', '101', '2020-01-15', 'hoat_dong'),
('HO002', 'Hộ gia đình Lê Văn Nam', '102', '2020-02-20', 'hoat_dong'),
('HO003', 'Hộ gia đình Phạm Thị Hoa', '201', '2020-03-10', 'hoat_dong'),
('HO004', 'Hộ gia đình Hoàng Văn Minh', '202', '2020-04-05', 'hoat_dong'),
('HO005', 'Hộ gia đình Nguyễn Văn An', '301', '2020-05-12', 'tam_dung'),
('HO006', 'Hộ gia đình Trần Thị Bình', '302', '2020-06-18', 'hoat_dong');

-- Thêm dữ liệu thành viên hộ
INSERT INTO thanh_vien_ho (ma_ho, cccd, vai_tro_trong_ho, ngay_tham_gia) VALUES
('HO001', '001234567891', 'chu_ho', '2020-01-15'),
('HO002', '001234567892', 'chu_ho', '2020-02-20'),
('HO003', '001234567893', 'chu_ho', '2020-03-10'),
('HO004', '001234567894', 'chu_ho', '2020-04-05'),
('HO005', '001234567891', 'thanh_vien', '2020-05-12'),
('HO006', '001234567892', 'thanh_vien', '2020-06-18');

-- Thêm dữ liệu tài sản chung cư
INSERT INTO tai_san_chung_cu (ten_tai_san, loai_tai_san, vi_tri, trang_thai, ngay_mua_sam, gia_tri) VALUES
('Thang máy A', 'thang_may', 'Tòa A', 'hoat_dong', '2019-12-01', 500000000),
('Thang máy B', 'thang_may', 'Tòa B', 'hoat_dong', '2019-12-01', 500000000),
('Hệ thống camera', 'an_ninh', 'Toàn bộ', 'hoat_dong', '2020-01-15', 200000000),
('Hệ thống chữa cháy', 'phong_chay', 'Toàn bộ', 'hoat_dong', '2020-02-01', 300000000),
('Sân chơi trẻ em', 'giai_tri', 'Tầng trệt', 'hoat_dong', '2020-03-01', 50000000),
('Khu vực để xe', 'do_xe', 'Tầng hầm', 'hoat_dong', '2020-04-01', 100000000);

-- Thêm dữ liệu dịch vụ
INSERT INTO dich_vu (ten_dich_vu, mo_ta, gia_dich_vu, don_vi_tinh, trang_thai) VALUES
('Phí quản lý', 'Phí quản lý chung cư hàng tháng', 500000, 'thang', 'hoat_dong'),
('Dịch vụ vệ sinh', 'Dịch vụ vệ sinh chung cư', 200000, 'thang', 'hoat_dong'),
('Dịch vụ bảo vệ', 'Dịch vụ bảo vệ 24/7', 300000, 'thang', 'hoat_dong'),
('Dịch vụ internet', 'Internet tốc độ cao', 150000, 'thang', 'hoat_dong'),
('Dịch vụ gym', 'Phòng tập gym', 100000, 'thang', 'hoat_dong'),
('Dịch vụ hồ bơi', 'Sử dụng hồ bơi', 80000, 'thang', 'hoat_dong');

-- Thêm dữ liệu đăng ký dịch vụ
INSERT INTO dang_ky_dich_vu (ma_ho, ma_dich_vu, ngay_dang_ky, trang_thai) VALUES
('HO001', 1, '2020-01-15', 'hoat_dong'),
('HO001', 2, '2020-01-15', 'hoat_dong'),
('HO001', 3, '2020-01-15', 'hoat_dong'),
('HO002', 1, '2020-02-20', 'hoat_dong'),
('HO002', 4, '2020-02-20', 'hoat_dong'),
('HO003', 1, '2020-03-10', 'hoat_dong'),
('HO003', 5, '2020-03-10', 'hoat_dong'),
('HO004', 1, '2020-04-05', 'hoat_dong'),
('HO004', 6, '2020-04-05', 'hoat_dong');

-- Thêm dữ liệu báo cáo sự cố
INSERT INTO bao_cao_su_co (tieu_de, noi_dung, cccd_nguoi_bao_cao, cccd_nguoi_nhap, muc_do_uu_tien, trang_thai, ngay_bao_cao, ket_qua_xu_ly) VALUES
('Thang máy bị hỏng', 'Thang máy A không hoạt động, cần sửa chữa ngay', '001234567891', '001234567890', 'khan_cap', 'da_hoan_thanh', '2024-01-15 09:30:00', 'Đã sửa chữa xong, thang máy hoạt động bình thường'),
('Mất nước', 'Không có nước từ sáng nay', '001234567892', '001234567890', 'cao', 'dang_xu_ly', '2024-01-16 08:00:00', 'Đang kiểm tra đường ống nước'),
('Tiếng ồn', 'Hàng xóm làm ồn vào ban đêm', '001234567893', '001234567890', 'binh_thuong', 'moi_tiep_nhan', '2024-01-17 22:30:00', NULL),
('Camera hỏng', 'Camera tầng 2 không hoạt động', '001234567894', '001234567890', 'binh_thuong', 'moi_tiep_nhan', '2024-01-18 14:20:00', NULL),
('Rác không được thu gom', 'Rác đã 2 ngày không được thu gom', '001234567891', '001234567890', 'cao', 'dang_xu_ly', '2024-01-19 10:15:00', 'Đã liên hệ công ty vệ sinh');

-- Thêm dữ liệu hóa đơn
INSERT INTO hoa_don (ma_ho, loai_hoa_don, so_tien, han_thanh_toan, trang_thai, ngay_tao, phuong_thuc_thanh_toan, ngay_thanh_toan) VALUES
('HO001', 'dich_vu', 700000, '2024-01-31', 'da_thanh_toan', '2024-01-01', 'chuyen_khoan', '2024-01-15'),
('HO001', 'phi_quan_ly', 500000, '2024-02-28', 'chua_thanh_toan', '2024-02-01', NULL, NULL),
('HO002', 'dich_vu', 650000, '2024-01-31', 'da_thanh_toan', '2024-01-01', 'tien_mat', '2024-01-20'),
('HO002', 'phi_quan_ly', 500000, '2024-02-28', 'chua_thanh_toan', '2024-02-01', NULL, NULL),
('HO003', 'dich_vu', 600000, '2024-01-31', 'da_thanh_toan', '2024-01-01', 'chuyen_khoan', '2024-01-18'),
('HO003', 'phi_quan_ly', 500000, '2024-02-28', 'chua_thanh_toan', '2024-02-01', NULL, NULL),
('HO004', 'dich_vu', 580000, '2024-01-31', 'da_thanh_toan', '2024-01-01', 'tien_mat', '2024-01-25'),
('HO004', 'phi_quan_ly', 500000, '2024-02-28', 'chua_thanh_toan', '2024-02-01', NULL, NULL),
('HO005', 'phi_quan_ly', 500000, '2024-01-31', 'qua_han', '2024-01-01', NULL, NULL),
('HO006', 'phi_quan_ly', 500000, '2024-01-31', 'da_thanh_toan', '2024-01-01', 'chuyen_khoan', '2024-01-30');

-- Thêm dữ liệu thông báo
INSERT INTO thong_bao (tieu_de, noi_dung, cccd_ban_quan_tri, loai_thong_bao, doi_tuong_nhan, trang_thai, ngay_tao_thong_bao) VALUES
('Thông báo nghỉ lễ', 'Chung cư sẽ nghỉ lễ Tết từ 29/1 đến 5/2. Các dịch vụ sẽ tạm dừng trong thời gian này.', '001234567890', 'quan_trong', 'tat_ca', 'hien', '2024-01-20 09:00:00'),
('Thông báo sửa chữa thang máy', 'Thang máy A sẽ được bảo trì từ 8h-12h ngày 25/1. Xin lỗi vì sự bất tiện.', '001234567890', 'binh_thuong', 'tat_ca', 'hien', '2024-01-22 14:30:00'),
('Thông báo thu phí', 'Phí quản lý tháng 2 sẽ được thu từ ngày 1/2. Vui lòng thanh toán đúng hạn.', '001234567890', 'binh_thuong', 'ho_gia_dinh', 'hien', '2024-01-25 10:00:00'),
('Cảnh báo an ninh', 'Có kẻ đáng ngờ xuất hiện trong khu vực. Cư dân cần cảnh giác và báo ngay cho bảo vệ.', '001234567890', 'khan_cap', 'tat_ca', 'hien', '2024-01-28 20:00:00'),
('Thông báo hoạt động cộng đồng', 'Sẽ có hoạt động giao lưu cộng đồng vào chủ nhật tuần sau. Mời tất cả cư dân tham gia.', '001234567890', 'binh_thuong', 'cu_dan', 'hien', '2024-01-30 16:00:00');

-- Thêm dữ liệu thông báo hộ
INSERT INTO thong_bao_ho (ma_thong_bao, ma_ho, trang_thai_doc) VALUES
(1, 'HO001', 'chua_doc'),
(1, 'HO002', 'chua_doc'),
(1, 'HO003', 'chua_doc'),
(1, 'HO004', 'chua_doc'),
(1, 'HO005', 'chua_doc'),
(1, 'HO006', 'chua_doc'),
(2, 'HO001', 'da_doc'),
(2, 'HO002', 'chua_doc'),
(2, 'HO003', 'chua_doc'),
(2, 'HO004', 'chua_doc'),
(2, 'HO005', 'chua_doc'),
(2, 'HO006', 'chua_doc'),
(3, 'HO001', 'chua_doc'),
(3, 'HO002', 'chua_doc'),
(3, 'HO003', 'chua_doc'),
(3, 'HO004', 'chua_doc'),
(3, 'HO005', 'chua_doc'),
(3, 'HO006', 'chua_doc'),
(4, 'HO001', 'chua_doc'),
(4, 'HO002', 'chua_doc'),
(4, 'HO003', 'chua_doc'),
(4, 'HO004', 'chua_doc'),
(4, 'HO005', 'chua_doc'),
(4, 'HO006', 'chua_doc'),
(5, 'HO001', 'chua_doc'),
(5, 'HO002', 'chua_doc'),
(5, 'HO003', 'chua_doc'),
(5, 'HO004', 'chua_doc');

-- Thêm dữ liệu phản hồi
INSERT INTO phan_hoi (ma_thong_bao, cccd_nguoi_phan_hoi, noi_dung_phan_hoi, ngay_phan_hoi) VALUES
(1, '001234567891', 'Cảm ơn ban quản trị đã thông báo sớm. Chúc mọi người năm mới vui vẻ!', '2024-01-20 10:30:00'),
(2, '001234567892', 'Thang máy A đã hoạt động bình thường sau khi sửa chữa. Cảm ơn!', '2024-01-22 15:45:00'),
(3, '001234567893', 'Tôi sẽ thanh toán phí quản lý đúng hạn. Cảm ơn thông báo.', '2024-01-25 11:20:00'),
(4, '001234567894', 'Cảm ơn cảnh báo. Tôi sẽ cảnh giác và báo ngay nếu có gì bất thường.', '2024-01-28 21:15:00'),
(5, '001234567891', 'Tôi sẽ tham gia hoạt động giao lưu. Rất mong được gặp mọi người!', '2024-01-30 17:30:00');

-- Cập nhật thống kê
UPDATE doi_tuong SET lan_dang_nhap_cuoi = NOW() WHERE cccd = '001234567890';

-- Hiển thị thông tin tài khoản test
SELECT '=== TÀI KHOẢN TEST ===' as info;
SELECT 'Admin: 001234567890 / admin123' as admin_account;
SELECT 'Cư dân: 001234567891 / admin123' as resident_account;
SELECT 'Kế toán: 001234567895 / admin123' as accountant_account;
SELECT 'Cơ quan chức năng: 001234567896 / admin123' as authority_account;

-- Hiển thị thống kê dữ liệu
SELECT '=== THỐNG KÊ DỮ LIỆU ===' as info;
SELECT COUNT(*) as total_users FROM doi_tuong;
SELECT COUNT(*) as total_households FROM ho_gia_dinh;
SELECT COUNT(*) as total_invoices FROM hoa_don;
SELECT COUNT(*) as total_reports FROM bao_cao_su_co;
SELECT COUNT(*) as total_notifications FROM thong_bao;
