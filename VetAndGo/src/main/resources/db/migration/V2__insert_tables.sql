INSERT INTO categories (name, description) VALUES
('Alimentación', 'Comida húmeda, piensos y snacks para todo tipo de mascotas.'),
('Juguetes', 'Juguetes interactivos, mordedores y peluches para el entretenimiento.'),
('Accesorios', 'Collares, correas, camas y transportines para el confort y seguridad.'),
('Higiene', 'Champús, cepillos y productos para el cuidado e higiene de tu mascota.'),
('Salud', 'Suplementos, antiparasitarios y productos de parafarmacia veterinaria.');

INSERT INTO products (name, category_id, description, stock, base_price, discount_percentage, final_price, image_url) VALUES
-- ALIMENTACIÓN
('Pienso Premium Perro 12kg', 1, 'Alimento completo y equilibrado para perros adultos.', 50, 45.99, 10.00, 41.39, 'https://imgs.search.brave.com/rwR6Wp7s39d_R84haqyPht1ftT2yatD-a01pNG_7ObQ/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9lcmEy/dnJtems1bi5leGFj/dGRuLmNvbS93cC1j/b250ZW50L3VwbG9h/ZHMvMjAyMS8wNi9E/aWdlc3Rpb24tc2Vu/c2libGUtMTBrZy1Q/aWVuc28tc3VwZXIt/cHJlbWl1bS1wZXJy/by1rYXNhbHVkaW50/ZWdyYWwtMTA4MHgx/MDgwcHgtMzYweDM2/MC5qcGc_c3RyaXA9/YWxsJmxvc3N5PTEm/c3NsPTE'),
('Comida Húmeda Gato Salmón', 1, 'Latas de comida húmeda gourmet sabor salmón.', 100, 1.99, 0.00, 1.99, 'https://imgs.search.brave.com/u8AlG24IfOszRivcQs9tJQNCTSit__9u_SsjBsLK7NU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/ZGVzcGVuc2EuZXMv/ZG9jdW1lbnRzLzEw/MTgwLzEwNzM2LzM3/MDI1Ml9NLmpwZw'),
('Snack Hueso Dental', 1, 'Snack masticable que ayuda a limpiar los dientes.', 200, 3.50, 15.00, 2.98, 'https://imgs.search.brave.com/1L9Bn4HLzIgr-AvCaTe9LjyJPYIIerBOINLsuJTss-s/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NzFCc3RwSFBkekwu/anBn'),
('Pienso Gato Esterilizado 3kg', 1, 'Alimento equilibrado para control de peso en gatos.', 60, 22.50, 8.00, 20.70, 'https://imgs.search.brave.com/R8mEDLhpRX1OpJU0akuzDWuSmw-Ycs3dr2mS1BY8vkQ/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9jbGFy/ZWwuaW1naXgubmV0/L21lZGlhL2NhdGFs/b2cvcHJvZHVjdC9B/L2wvQWxpbWVudG9f/R2F0b19Fc3Rlcmls/aXphZG9fVWx0aW1h/XzEuNUtnX2IzZGY2/NzViOGQ1ODk3YjYy/YmE0MDA0MTlmYTgw/NjQ5Y2VjZWYyYjBf/MTk0NzY0XzEuanBn/P2F1dG89Zm9ybWF0/JmZpdD1tYXgmdz0x/NzAwJnE9MTAw'),
('Snack Natural Pollo', 1, 'Tiras de pechuga de pollo deshidratada 100% natural.', 120, 5.99, 0.00, 5.99, 'https://imgs.search.brave.com/vsHbqP4-zc2Y6JPwhDQxeA1O4-4A53WdGhRB4cfZGr4/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NzF6ZmdEcGp3Y0wu/anBn'),

-- JUGUETES Y ACCESORIOS
('Rascador Árbol para Gatos', 2, 'Torre rascador con múltiples niveles y cueva.', 20, 59.90, 20.00, 47.92, 'https://imgs.search.brave.com/hjbFjjXuCX3BHsDKsZldVRPLyfKIGCXwIDkJp4MtxPU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/dGllbmRhbmltYWwu/ZXMvZHcvaW1hZ2Uv/djIvQkRMUV9QUkQv/b24vZGVtYW5kd2Fy/ZS5zdGF0aWMvLS9T/aXRlcy1raXdva28t/bWFzdGVyLWNhdGFs/b2cvZGVmYXVsdC9k/d2RiMmFlMWE2L2lt/YWdlcy9NYXJrZXRw/bGFjZS84NDM1NDI4/NzM3NDEyLmpwZz9z/dz0yNzUmc2g9Mjc1/JnNtPWZpdA'),
('Kong Classic Rojo', 2, 'Juguete de caucho resistente para rellenar con premios.', 70, 14.99, 0.00, 14.99, 'https://imgs.search.brave.com/zeX0M2CS39_d0vzIMp5v2Oo44RvLbqUzPmpxhyOO3s8/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/YW5pbWFsdWpvcy5j/b20vMTU1Mi1tZWRp/dW1fZGVmYXVsdC9r/b25nLWNsYXNzaWMt/cm9qby1qdWd1ZXRl/LXJlbGxlbmFibGUt/cGFyYS1wZXJyb3Mu/anBn'),
('Cuerda Nudo Triple', 2, 'Juguete de cuerda de algodón para tirar y aflojar.', 110, 6.50, 0.00, 6.50, 'https://imgs.search.brave.com/kP6QLAqxAY7bkyTx9_m7LFYgqpMUO-TRqnwq4HlIW2I/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/MzF1eWktQWxLQVMu/anBn'),
('Túnel Plegable Gatos', 2, 'Túnel de poliéster con tres entradas y mirilla.', 40, 15.90, 25.00, 11.93, 'https://imgs.search.brave.com/sQmB5qzyfwgR_YccyrR50_CL0BY9mtsomqm31y1Q7KI/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9jZG4u/bWFub21hbm8uY29t/L3R1bmVsLXBhcmEt/Z2F0b3MtY3J1amll/bnRlLWNvbi1ib2xh/cy1qdWd1ZXRlLXBs/ZWdhYmxlLXBhcmEt/Z2F0b3MtbmVncm8t/UC00MDM2MDc3OC0x/NTUzODUxODZfMS5q/cGc'),
('Frisbee de Goma', 2, 'Disco volador flexible para juegos en el parque.', 65, 9.99, 0.00, 9.99, 'https://imgs.search.brave.com/P76q4USBwmllSmamcQ0tk-a07MpcsqVFkEaW5X55DY4/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9zcGFj/ZS10aGVwcm9maXQu/bnljMy5kaWdpdGFs/b2NlYW5zcGFjZXMu/Y29tL3B1YmxpYy9Q/cm9kdWN0cy9mcmlz/YmVlLXBhcmEtcGVy/cm9zLWRlLWdvbWEt/ZXZhXzY1NzkyODRj/ZjMzMmIud2VicA'),

-- DESCANSO Y TRANSPORTE
('Cama Suave XL', 3, 'Cama acolchada y mullida para perros grandes.', 15, 34.95, 0.00, 34.95, 'https://imgs.search.brave.com/AOzMgHleJ27bkTXVsGbif4t3pilv-odfJfK5tXdsS2Y/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NTFqK3Mxak9EWkwu/anBn'),
('Transportín Viaje', 3, 'Transportín homologado para viajes, seguro y ventilado.', 25, 29.99, 0.00, 29.99, 'https://imgs.search.brave.com/bqw3mLL0SqTTWL0wQ-Z5gGtIdr0EDkv0l67TgsmcHqE/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NDFqQzNoS09mbEwu/anBn'),
('Correa Extensible 5m', 3, 'Correa retráctil con freno de seguridad.', 55, 18.95, 0.00, 18.95, 'https://imgs.search.brave.com/uyFoQTjwKbyLgq7nB_ovuodfMBUW1Wmead5n1Jcq9fw/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9zMS5z/dGF0aWNsZC5jb20v/MjAyMS8wMy8wOC9j/b3JyZWEtZXh0ZW5z/aWJsZS1wYXJhLXBl/cnJvcy01LW1ldHJv/cy1vdWdlLmpwZw'),
('Arnés Acolchado Azul', 3, 'Arnés ergonómico reflectante talla mediana.', 48, 24.50, 15.00, 20.83, 'https://imgs.search.brave.com/2RvDRFwYEGeAznk-F8yfRV62hmQaKvLoQy-amHfNdH4/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/bnVuYXBldC5jb20v/MTU1MS1ob21lX2Rl/ZmF1bHQvcmVkLWRp/bmdvLWFybmVzLWFj/b2xjaGFkby1henVs/LmpwZw'),
('Fuente de Agua 2L', 3, 'Bebedero automático con filtro de carbón.', 30, 32.99, 0.00, 32.99, 'https://imgs.search.brave.com/6Weh411Jw4IGHGV6gkzgn_s4PwuYgUYa2IXGZCwCMnw/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/MzFucFdYVy0xR0wu/anBn'),
('Comedero Acero Inox', 3, 'Bol de acero inoxidable con base antideslizante.', 150, 8.50, 0.00, 8.50, 'https://imgs.search.brave.com/ORByCzCVTvOMnnF9qUw4XzG7zV3TA38BB_KEllbT2bA/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NjFuSmljQTNuVkwu/anBn'),

-- HIGIENE
('Champú Piel Sensible', 4, 'Champú hipoalergénico con aloe vera.', 40, 9.99, 0.00, 9.99, 'https://imgs.search.brave.com/ek3XxX9WabdgXywy985avYRGy4UUzUcIE24b5k1XVBg/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/MzExK1l0SmVnZkwu/anBn'),
('Toallitas Higiénicas', 4, 'Pack 80 toallitas para limpieza de patas y cara.', 75, 5.50, 0.00, 5.50, 'https://imgs.search.brave.com/OqFXIe6lEHmEl3yr_SDXwnyC_kKLWlf_PC0Xk9ceGjs/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pbWFn/ZXMtbmEuc3NsLWlt/YWdlcy1hbWF6b24u/Y29tL2ltYWdlcy9J/Lzcxd0wteEdpMkpM/LmpwZw'),
('Cortaúñas Ergonómico', 4, 'Tijera para uñas con tope de seguridad.', 60, 11.90, 0.00, 11.90, 'https://imgs.search.brave.com/rsdMimEX5wwq38EPubRYjDjC9WPV3b0GJhSCT8mL7hU/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/NTE3RjVWZG9XSEwu/anBn'),
('Spray Quita-Olores', 4, 'Eliminador enzimático de manchas y olores.', 45, 8.90, 0.00, 8.90, 'https://imgs.search.brave.com/oijqB4SMbHaibVsDzhVZpGP7AKBch-vgQezhrL49J2w/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/b25saW5lem9vLmVz/L21lZGlhLzA3L2Uy/L2Y4LzE3MzQ4OTg3/NTMvZWNvZG9yLXVm/MjAwLWVjb2Rvci1l/Y29wZXQtdXJpbmRl/dGVrdG9yLWh1bmRl/ZmVybmhhbHRlc3By/YXkuanBnP3RzPTE3/MzU0OTIxNDc'),
('Kit Cepillo Dental', 4, 'Pasta de dientes sabor carne y dos cepillos.', 70, 6.99, 0.00, 6.99, 'https://imgs.search.brave.com/aoy-0S5PpgyNsOqUEp4pkqKoctOjZfC4gZZETH5f4TM/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pbWFn/ZXMtZXUuc3NsLWlt/YWdlcy1hbWF6b24u/Y29tL2ltYWdlcy9J/LzcxaGc5T3RqNXJM/Ll9BQ19VTDMwMF9T/UjMwMCwyMDBfLmpw/Zw'),

-- SALUD
('Vitaminas Multifunción', 5, 'Complejo vitamínico completo en tableta masticable.', 88, 12.50, 0.00, 12.50, 'https://imgs.search.brave.com/xSNbDYk0ELxzShxbLhHFMFg4gTkFtZRTB99adKW617Y/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pbWFn/ZXMtbmEuc3NsLWlt/YWdlcy1hbWF6b24u/Y29tL2ltYWdlcy9J/Lzgxc1NMVUxvVGFM/LmpwZw'),
('Aceite de Salmón Omega-3', 5, 'Suplemento líquido para un pelaje brillante.', 72, 14.99, 0.00, 14.99, 'https://imgs.search.brave.com/3TdRPQaQ7k_u0QfXuNUATS-YnUMWRUmWb8hCEycMdHY/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly93d3cu/dW5tdW5kb2FncmFu/ZWwuZXMvMjU1NS1s/YXJnZV9kZWZhdWx0/L2FjZWl0ZS1kZS1z/YWxtb24tb21lZ2Et/My0yNTBnLmpwZw'),
('Vendaje Cohesivo', 5, 'Venda elástica que no se pega al pelo.', 90, 7.50, 0.00, 7.50, 'https://imgs.search.brave.com/VrZ_y_0nzLtn64lAMcAOOxAgX4XamdYhP8f9iAXjPNE/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9tLm1l/ZGlhLWFtYXpvbi5j/b20vaW1hZ2VzL0kv/ODFUbWtLck85RFMu/anBn');

-- ('user1', 'pass', 'USER'),
-- ('admin', 'admin', 'ADMIN');
