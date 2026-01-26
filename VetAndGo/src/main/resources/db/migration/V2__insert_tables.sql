INSERT INTO categories (name, description) VALUES
('Alimentación', 'Comida húmeda, piensos y snacks para todo tipo de mascotas.'),
('Juguetes', 'Juguetes interactivos, mordedores y peluches para el entretenimiento.'),
('Accesorios', 'Collares, correas, camas y transportines para el confort y seguridad.'),
('Higiene', 'Champús, cepillos y productos para el cuidado e higiene de tu mascota.'),
('Salud', 'Suplementos, antiparasitarios y productos de parafarmacia veterinaria.');

INSERT INTO products (name, category_id, description, stock, base_price, discount_percentage, final_price, image_url) VALUES
('Pienso Premium Perro 12kg', 1, 'Alimento completo y equilibrado para perros adultos de todas las razas.', 50, 45.99, 10.00, 41.39, 'https://images.unsplash.com/photo-1568640347023-a616a30bc3bd?auto=format&fit=crop&w=800&q=80'),
('Comida Húmeda Gato Salmón', 1, 'Latas de comida húmeda gourmet sabor salmón para gatos exigentes.', 100, 1.99, 0.00, 1.99, 'https://images.unsplash.com/photo-1583337130417-3346a1be7dee?auto=format&fit=crop&w=800&q=80'),
('Snack Hueso Dental', 1, 'Snack masticable que ayuda a limpiar los dientes y refrescar el aliento.', 200, 3.50, 15.00, 2.98, 'https://images.unsplash.com/photo-1582798358481-d199fb7347bb?auto=format&fit=crop&w=800&q=80'),
('Pelota Resistente', 2, 'Pelota de goma casi indestructible para perros activos.', 80, 8.99, 0.00, 8.99, 'https://images.unsplash.com/photo-1615266895738-11f1371cd7e5?auto=format&fit=crop&w=800&q=80'),
('Rascador Árbol para Gatos', 2, 'Torre rascador con múltiples niveles y cueva para dormir.', 20, 59.90, 20.00, 47.92, 'https://images.unsplash.com/photo-1545249390-6bdfa286032f?auto=format&fit=crop&w=800&q=80'),
('Ratón de Peluche', 2, 'Juguete clásico con catnip para estimular el instinto cazador.', 150, 2.99, 0.00, 2.99, 'https://images.unsplash.com/photo-1615486511484-92e172cc416d?auto=format&fit=crop&w=800&q=80'),
('Cama Suave XL', 3, 'Cama acolchada y mullida para perros grandes, desenfundable y lavable.', 15, 34.95, 0.00, 34.95, 'https://images.unsplash.com/photo-1541599540903-216a46ca1dc0?auto=format&fit=crop&w=800&q=80'),
('Collar Ajustable Reflectante', 3, 'Collar de nylon resistente con bandas reflectantes para paseos nocturnos.', 60, 12.50, 5.00, 11.88, 'https://images.unsplash.com/photo-1605639156481-244775d6f803?auto=format&fit=crop&w=800&q=80'),
('Transportín Viaje', 3, 'Transportín homologado para viajes en avión y coche, seguro y ventilado.', 25, 29.99, 0.00, 29.99, 'https://images.unsplash.com/photo-1601758228041-f3b2795255f1?auto=format&fit=crop&w=800&q=80'),
('Champú Piel Sensible', 4, 'Champú hipoalergénico con aloe vera para mascotas con piel delicada.', 40, 9.99, 0.00, 9.99, 'https://images.unsplash.com/photo-1516734212186-a967f81ad0d7?auto=format&fit=crop&w=800&q=80'),
('Cepillo Carda Suave', 4, 'Elimina el pelo muerto y desenreda sin dañar la piel.', 50, 7.50, 10.00, 6.75, 'https://images.unsplash.com/photo-1623903178351-171804f3dbda?auto=format&fit=crop&w=800&q=80'),
('Antiparasitario Pipeta', 5, 'Protección mensual contra pulgas, garrapatas y mosquitos.', 100, 8.95, 0.00, 8.95, 'https://images.unsplash.com/photo-1576201836106-db1758fd1c97?auto=format&fit=crop&w=800&q=80'),
-- Más productos de Alimentación
('Pienso Junior Cachorros 5kg', 1, 'Alimento especial para cachorros con DHA y calcio para un crecimiento saludable.', 45, 28.99, 0.00, 28.99, 'https://images.unsplash.com/photo-1589924691995-400dc9ecc119?auto=format&fit=crop&w=800&q=80'),
('Comida Húmeda Perro Pollo', 1, 'Latas de 400g con trozos de pollo natural en salsa.', 85, 2.45, 0.00, 2.45, 'https://images.unsplash.com/photo-1583337130417-3346a1be7dee?auto=format&fit=crop&w=800&q=80'),
('Pienso Gato Esterilizado 3kg', 1, 'Alimento completo para gatos esterilizados con control de peso.', 60, 22.50, 8.00, 20.70, 'https://images.unsplash.com/photo-1611003228941-98852ba62227?auto=format&fit=crop&w=800&q=80'),
('Snack Natural Pollo Deshidratado', 1, 'Tiras de pechuga de pollo 100% natural sin conservantes.', 120, 5.99, 0.00, 5.99, 'https://images.unsplash.com/photo-1628009368231-7bb7cfcb0def?auto=format&fit=crop&w=800&q=80'),
('Pienso Senior Perro 10kg', 1, 'Fórmula especial para perros mayores de 7 años con condroprotectores.', 35, 42.90, 12.00, 37.75, 'https://images.unsplash.com/photo-1587300003388-59208cc962cb?auto=format&fit=crop&w=800&q=80'),
('Snack Dental Gato', 1, 'Galletas crujientes que ayudan a reducir el sarro en gatos.', 95, 4.20, 0.00, 4.20, 'https://images.unsplash.com/photo-1548681528-6a5c45b66b42?auto=format&fit=crop&w=800&q=80'),
-- Más productos de Juguetes
('Kong Rellenable Classic', 2, 'Juguete interactivo para rellenar con premios y mantener entretenido.', 70, 14.99, 0.00, 14.99, 'https://images.unsplash.com/photo-1603201667230-bd9574a4c976?auto=format&fit=crop&w=800&q=80'),
('Cuerda Nudo Triple', 2, 'Juguete de cuerda resistente ideal para juegos de tira y afloja.', 110, 6.50, 0.00, 6.50, 'https://images.unsplash.com/photo-1583511655857-d19b40a7a54e?auto=format&fit=crop&w=800&q=80'),
('Túnel Plegable para Gatos', 2, 'Túnel de tela con múltiples entradas para juego y escondite.', 40, 15.90, 25.00, 11.93, 'https://images.unsplash.com/photo-1573865526739-10c1d3a1f0cc?auto=format&fit=crop&w=800&q=80'),
('Disco Volador para Perros', 2, 'Frisbee suave y flexible para juegos al aire libre.', 65, 9.99, 0.00, 9.99, 'https://images.unsplash.com/photo-1587764379873-97837921fd44?auto=format&fit=crop&w=800&q=80'),
('Peluche Pato con Sonido', 2, 'Peluche suave con sonido chirriante para estimular el juego.', 90, 7.99, 0.00, 7.99, 'https://images.unsplash.com/photo-1591769225440-811ad7d6eab3?auto=format&fit=crop&w=800&q=80'),
-- Más productos de Accesorios
('Correa Extensible 5m', 3, 'Correa retráctil con freno automático para paseos cómodos.', 55, 18.95, 0.00, 18.95, 'https://images.unsplash.com/photo-1622290291468-a28f7a7e8f4f?auto=format&fit=crop&w=800&q=80'),
('Arnés Acolchado Talla M', 3, 'Arnés ergonómico con acolchado en pecho y espalda.', 48, 24.50, 15.00, 20.83, 'https://images.unsplash.com/photo-1601758123927-4f99ec3ac23e?auto=format&fit=crop&w=800&q=80'),
('Bebedero Automático 2L', 3, 'Fuente de agua con filtro para mantener el agua fresca y oxigenada.', 30, 32.99, 0.00, 32.99, 'https://images.unsplash.com/photo-1589789602567-b85e92a0fde1?auto=format&fit=crop&w=800&q=80'),
('Comedero Elevado Doble', 3, 'Set de comedero y bebedero elevados, altura ajustable.', 25, 27.90, 0.00, 27.90, 'https://images.unsplash.com/photo-1616698002951-fd984c7af527?auto=format&fit=crop&w=800&q=80'),
('Manta Térmica para Mascotas', 3, 'Manta suave con tejido térmico que retiene el calor corporal.', 38, 19.99, 0.00, 19.99, 'https://images.unsplash.com/photo-1615751072497-5f5169febe17?auto=format&fit=crop&w=800&q=80'),
('Placa Identificativa Grabada', 3, 'Placa personalizable de acero inoxidable con grabado láser.', 200, 6.99, 0.00, 6.99, 'https://images.unsplash.com/photo-1600077106724-946750eeaf3c?auto=format&fit=crop&w=800&q=80'),
-- Más productos de Higiene
('Toallitas Húmedas Multiusos', 4, 'Pack de 80 toallitas para limpieza rápida de patas y pelo.', 75, 5.50, 0.00, 5.50, 'https://images.unsplash.com/photo-1609684031552-2c9e4394c8cb?auto=format&fit=crop&w=800&q=80'),
('Cortaúñas Profesional', 4, 'Cortaúñas de acero inoxidable con protección de seguridad.', 60, 11.90, 0.00, 11.90, 'https://images.unsplash.com/photo-1606214174585-fe31582dc6ee?auto=format&fit=crop&w=800&q=80'),
('Champú Antipulgas Natural', 4, 'Champú con aceites esenciales de citronela y neem.', 52, 12.99, 18.00, 10.65, 'https://images.unsplash.com/photo-1620843002805-05a08cb72f57?auto=format&fit=crop&w=800&q=80'),
('Spray Eliminador de Olores', 4, 'Neutralizador enzimático de olores para hogar y tejidos.', 45, 8.90, 0.00, 8.90, 'https://images.unsplash.com/photo-1581578731548-c64695cc6952?auto=format&fit=crop&w=800&q=80'),
('Cepillo de Dientes para Perros', 4, 'Kit con cepillo de doble cabezal y pasta dental sabor carne.', 70, 6.99, 0.00, 6.99, 'https://images.unsplash.com/photo-1628288420616-1fd9a1635446?auto=format&fit=crop&w=800&q=80'),
-- Más productos de Salud
('Suplemento Articular', 5, 'Condroprotector con glucosamina y condroitina para articulaciones.', 80, 24.99, 10.00, 22.49, 'https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?auto=format&fit=crop&w=800&q=80'),
('Collar Antiparasitario 8 meses', 5, 'Protección prolongada contra parásitos externos con efecto repelente.', 65, 16.50, 0.00, 16.50, 'https://images.unsplash.com/photo-1576201836106-db1758fd1c97?auto=format&fit=crop&w=800&q=80'),
('Probióticos Digestivos', 5, 'Suplemento con probióticos para mejorar la salud intestinal.', 55, 18.90, 0.00, 18.90, 'https://images.unsplash.com/photo-1607619056574-7b8d3ee536b2?auto=format&fit=crop&w=800&q=80'),
('Vendaje Autoadhesivo', 5, 'Venda elástica veterinaria que se adhiere a sí misma, pack 3 unidades.', 90, 7.50, 0.00, 7.50, 'https://images.unsplash.com/photo-1603398938378-e54eab446dde?auto=format&fit=crop&w=800&q=80'),
('Omega 3 para Mascotas', 5, 'Aceite de salmón rico en ácidos grasos para piel y pelaje brillante.', 72, 14.99, 0.00, 14.99, 'https://images.unsplash.com/photo-1585421514738-01798e348b17?auto=format&fit=crop&w=800&q=80'),
('Vitaminas Multifunción', 5, 'Complejo vitamínico completo en formato tableta masticable.', 88, 12.50, 0.00, 12.50, 'https://images.unsplash.com/photo-1607619056574-7b8d3ee536b2?auto=format&fit=crop&w=800&q=80');
-- INSERT INTO clients (username, password, role) VALUES
-- ('user1', 'pass', 'USER'),
-- ('admin', 'admin', 'ADMIN');
