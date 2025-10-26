-- Tạo bảng chi_so_dien_nuoc nếu chưa tồn tại
CREATE TABLE IF NOT EXISTS chi_so_dien_nuoc (
    ma_chi_so SERIAL PRIMARY KEY,
    ma_ho VARCHAR(20) NOT NULL,
    ky_thanh_toan VARCHAR(20) NOT NULL, -- VD: "01/2024"
    dien_cu INTEGER NOT NULL DEFAULT 0,
    dien_moi INTEGER NOT NULL DEFAULT 0,
    nuoc_cu INTEGER NOT NULL DEFAULT 0,
    nuoc_moi INTEGER NOT NULL DEFAULT 0,
    don_gia_dien DECIMAL(15,2) DEFAULT 3500.00, -- VNĐ/số
    don_gia_nuoc DECIMAL(15,2) DEFAULT 10000.00, -- VNĐ/khối
    tien_dien DECIMAL(15,2) DEFAULT 0,
    tien_nuoc DECIMAL(15,2) DEFAULT 0,
    tien_dich_vu DECIMAL(15,2) DEFAULT 0, -- Tổng tiền dịch vụ khác
    tong_tien DECIMAL(15,2) DEFAULT 0,
    ngay_nhap TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ghi_chu TEXT,
    CONSTRAINT fk_ma_ho_chiso FOREIGN KEY (ma_ho) 
        REFERENCES ho_gia_dinh(ma_ho) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT check_dien_cu_moi CHECK (dien_moi >= dien_cu),
    CONSTRAINT check_nuoc_cu_moi CHECK (nuoc_moi >= nuoc_cu),
    CONSTRAINT check_chi_so_duong CHECK (dien_cu >= 0 AND dien_moi >= 0 AND nuoc_cu >= 0 AND nuoc_moi >= 0),
    CONSTRAINT check_tien_duong CHECK (tien_dien >= 0 AND tien_nuoc >= 0 AND tien_dich_vu >= 0 AND tong_tien >= 0),
    CONSTRAINT unique_ma_ho_ky UNIQUE (ma_ho, ky_thanh_toan)
);

