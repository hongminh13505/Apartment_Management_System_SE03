package com.apartment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "doi_tuong")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoiTuong {
    
    @Id
    @Column(name = "cccd", length = 12)
    private String cccd;
    
    @Column(name = "mat_khau", nullable = false)
    private String matKhau;
    
    @Column(name = "vai_tro", nullable = false, length = 20)
    private String vaiTro;
    
    @Column(name = "la_cu_dan", nullable = false)
    private Boolean laCuDan;
    
    @Column(name = "ho_va_ten", nullable = false, length = 100)
    private String hoVaTen;
    
    @Column(name = "ngay_sinh", nullable = false)
    private LocalDate ngaySinh;
    
    @Column(name = "gioi_tinh", length = 10)
    private String gioiTinh;
    
    @Column(name = "que_quan", columnDefinition = "TEXT")
    private String queQuan;
    
    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "nghe_nghiep", length = 100)
    private String ngheNghiep;
    
    @Column(name = "trang_thai_tai_khoan", length = 20)
    private String trangThaiTaiKhoan = "hoat_dong";
    
    @Column(name = "trang_thai_dan_cu", length = 20)
    private String trangThaiDanCu;
    
    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao;
    
    @Column(name = "lan_dang_nhap_cuoi")
    private LocalDateTime lanDangNhapCuoi;
    
    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;
    
    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
        ngayCapNhat = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
    
    // Manual getter/setter methods for Lombok compatibility
    public String getMatKhau() {
        return matKhau;
    }
    
    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    public LocalDateTime getLanDangNhapCuoi() {
        return lanDangNhapCuoi;
    }
    
    public void setLanDangNhapCuoi(LocalDateTime lanDangNhapCuoi) {
        this.lanDangNhapCuoi = lanDangNhapCuoi;
    }
    
    public String getCccd() {
        return cccd;
    }
    
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
    
    public String getVaiTro() {
        return vaiTro;
    }
    
    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }
    
    public Boolean getLaCuDan() {
        return laCuDan;
    }
    
    public void setLaCuDan(Boolean laCuDan) {
        this.laCuDan = laCuDan;
    }
    
    public String getHoVaTen() {
        return hoVaTen;
    }
    
    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public String getQueQuan() {
        return queQuan;
    }
    
    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }
    
    public String getSoDienThoai() {
        return soDienThoai;
    }
    
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNgheNghiep() {
        return ngheNghiep;
    }
    
    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }
    
    public String getTrangThaiTaiKhoan() {
        return trangThaiTaiKhoan;
    }
    
    public void setTrangThaiTaiKhoan(String trangThaiTaiKhoan) {
        this.trangThaiTaiKhoan = trangThaiTaiKhoan;
    }
    
    public String getTrangThaiDanCu() {
        return trangThaiDanCu;
    }
    
    public void setTrangThaiDanCu(String trangThaiDanCu) {
        this.trangThaiDanCu = trangThaiDanCu;
    }
    
    public LocalDateTime getNgayTao() {
        return ngayTao;
    }
    
    public void setNgayTao(LocalDateTime ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }
    
    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}


