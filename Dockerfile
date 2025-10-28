# Sử dụng image JDK 17 chính thức
FROM openjdk:17-jdk-slim

# Tạo thư mục app
WORKDIR /app

# Copy file JAR từ target/
COPY target/apartment-management-1.0.0.jar app.jar

# Expose port (Render tự chọn port, nên dùng biến môi trường)
EXPOSE 8080

# Lệnh khởi động app
ENTRYPOINT ["java", "-jar", "app.jar"]