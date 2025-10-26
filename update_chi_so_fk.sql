-- Thêm cột ma_hoa_don và foreign key vào bảng chi_so_dien_nuoc (nếu đã tồn tại)
-- Chạy script này nếu bảng chi_so_dien_nuoc đã được tạo trước đó

-- Thêm cột ma_hoa_don
ALTER TABLE chi_so_dien_nuoc 
ADD COLUMN IF NOT EXISTS ma_hoa_don INTEGER;

-- Thêm foreign key constraint (DROP trước nếu tồn tại)
ALTER TABLE chi_so_dien_nuoc 
DROP CONSTRAINT IF EXISTS fk_ma_hoa_don_chiso;

ALTER TABLE chi_so_dien_nuoc 
ADD CONSTRAINT fk_ma_hoa_don_chiso 
FOREIGN KEY (ma_hoa_don) 
REFERENCES hoa_don(ma_hoa_don) 
ON DELETE SET NULL 
ON UPDATE CASCADE;

