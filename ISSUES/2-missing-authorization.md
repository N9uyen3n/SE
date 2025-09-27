# Vấn đề 2: Thiếu phân quyền truy cập

**Mức độ ưu tiên:** Cao

**Mô tả:**
Các API cho các chức năng quản trị, chẳng hạn như thêm (`addBook`), cập nhật (`updateBook`), và xóa (`deleteBook`) sách trong `BookService` và `BookController`, không có bất kỳ cơ chế phân quyền nào. Điều này có nghĩa là bất kỳ người dùng nào đã xác thực (hoặc thậm chí là người dùng ẩn danh, tùy thuộc vào cấu hình bảo mật chung) đều có thể thực hiện các hành động này.

**Hành động cần thực hiện:**
1. Triển khai phân quyền dựa trên vai trò (role-based authorization) sử dụng Spring Security.
2. Đảm bảo rằng mô hình `User` có một trường để lưu trữ vai trò (ví dụ: `ROLE_USER`, `ROLE_ADMIN`).
3. Áp dụng các chú thích (annotation) như `@PreAuthorize("hasRole('ADMIN')")` hoặc cấu hình trong `SecurityFilterChain` để bảo vệ các endpoint quản trị, chỉ cho phép người dùng có vai trò `ADMIN` truy cập.
