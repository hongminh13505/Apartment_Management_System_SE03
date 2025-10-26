-- Thêm loại hóa đơn 'dien_nuoc' vào constraint của bảng hoa_don
ALTER TABLE hoa_don DROP CONSTRAINT IF EXISTS hoa_don_loai_hoa_don_check;
ALTER TABLE hoa_don ADD CONSTRAINT hoa_don_loai_hoa_don_check 
    CHECK (loai_hoa_don IN ('dich_vu', 'dien_nuoc', 'sua_chua', 'phat', 'khac'));

