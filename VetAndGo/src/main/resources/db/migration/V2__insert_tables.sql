INSERT INTO categories (name, description) VALUES
('Medications', 'Prescription and over-the-counter medications for pets including antibiotics, pain relief, and supplements.'),
('Surgical Supplies', 'Sterile surgical instruments, sutures, bandages, and wound care materials for veterinary procedures.'),
('Diagnostics', 'Diagnostic equipment and test kits including thermometers, otoscopes, ophthalmoscopes, and laboratory tests.'),
('Vaccines', 'Immunizations and vaccines for dogs, cats, and other animals to prevent common diseases.'),
('Dental Care', 'Dental products including cleaning tools, toothpaste, and oral care solutions for animal dentistry.'),
('Grooming & Hygiene', 'Shampoos, conditioners, nail clippers, and grooming supplies for pet care and cleanliness.'),
('Nutrition & Supplements', 'Therapeutic diets, vitamin supplements, and nutritional products for specific health conditions.'),
('Equipment & Instruments', 'Examination tables, scales, oxygen delivery systems, and other veterinary equipment.'),
('Orthopedic Supplies', 'Splints, casts, braces, and orthopedic support devices for fracture treatment and mobility issues.'),
('Anesthesia & Monitoring', 'Anesthetic agents, monitoring equipment, and supplies for safe surgical procedures.');

INSERT INTO products (name, category_id, description, stock, price) VALUES
-- Medications (Category 1)
('Amoxicillin 250mg Tablets', 1, 'Broad-spectrum antibiotic for bacterial infections in dogs and cats. 100 tablets per bottle.', 75, 24.99),
('Carprofen 100mg (Rimadyl) - 60 tablets', 1, 'Nonsteroidal anti-inflammatory pain relief medication for post-operative and chronic pain.', 50, 89.99),
('Doxycycline 100mg - 100 capsules', 1, 'Antibiotic used for treating respiratory and skin infections in small animals.', 60, 34.50),
('Metronidazole 250mg - 30 tablets', 1, 'Antiprotozoal and antibacterial medication for gastrointestinal infections.', 85, 19.99),
('Fluconazole 50mg - 20 capsules', 1, 'Antifungal medication for treating systemic and dermatophyte infections.', 40, 45.99),

-- Surgical Supplies (Category 2)
('Absorbable Surgical Sutures Size 0 - 12 count', 2, 'Sterile absorbable chromic gut sutures for internal wound closure. Box of 12.', 45, 28.50),
('Sterile Surgical Gloves Size L - 50 pairs', 2, 'Latex-free surgical gloves for safe surgical procedures. Box of 50 pairs.', 120, 15.99),
('Sterile Gauze Pads 4x4 - 200 count', 2, 'Non-adherent sterile gauze for wound dressing and cleaning. Pack of 200.', 200, 12.99),
('Self-Adhesive Elastic Bandage 2 inch', 2, 'Flexible adhesive bandage for securing dressings and supportive wrapping. Roll of 10.', 95, 8.99),
('Surgical Drapes 18x26 - 50 count', 2, 'Sterile surgical field drapes for maintaining aseptic conditions. Pack of 50.', 75, 22.50),

-- Diagnostics (Category 3)
('Digital Thermometer with Flexible Probe', 3, 'Fast and accurate temperature measurement for routine examinations and health monitoring.', 55, 42.99),
('Otoscope with Speculum Set', 3, 'Complete otoscope with multiple specula for ear canal examination and disease detection.', 25, 89.99),
('Indirect Ophthalmoscope', 3, 'Professional-grade ophthalmoscope for retinal and optic nerve examination.', 12, 349.99),
('Portable Ultrasound Scanner Microconvex 3.5MHz', 3, 'Compact ultrasound probe for abdominal and cardiac imaging during examinations.', 8, 1299.99),
('Blood Pressure Monitor with Cuff', 3, 'Non-invasive blood pressure monitoring for anesthetic and cardiovascular assessment.', 18, 165.99),

-- Vaccines (Category 4)
('DHPP Vaccine (4 in 1) - 10 dose vial', 4, 'Combination vaccine for Distemper, Hepatitis, Parvovirus, and Parainfluenza in dogs.', 85, 12.99),
('FVRCP Vaccine (3 in 1) - 10 dose vial', 4, 'Feline vaccine protecting against Feline Viral Rhinotracheitis, Calicivirus, and Panleukopenia.', 70, 14.99),
('Rabies Vaccine 1mL - 10 dose vial', 4, 'Inactivated rabies vaccine for dogs and cats. Required by law in most regions.', 150, 8.99),
('Bordetella Vaccine - 10 dose vial', 4, 'Intranasal vaccine for kennel cough protection in dogs.', 60, 11.50),
('Leptospirosis Vaccine - 10 dose vial', 4, 'Protective vaccine against leptospirosis bacteria in dogs.', 50, 13.99),

-- Dental Care (Category 5)
('Ultrasonic Dental Scaler with Tips', 5, 'Professional ultrasonic scaler for efficient plaque and tartar removal. Includes 5 interchangeable tips.', 15, 299.99),
('Enzymatic Toothpaste for Pets 3oz', 5, 'Pet-safe enzymatic toothpaste that helps reduce tartar buildup and freshen breath. Poultry flavor.', 120, 7.99),
('Dental Extraction Forceps Set - 6 pieces', 5, 'Complete set of specialized forceps for extracting teeth of various sizes.', 8, 149.99),
('Dental Mirror with Light and Handle', 5, 'Magnifying dental mirror with built-in LED light for proper visualization during dental procedures.', 20, 34.50),
('Periodontal Probe and Explorer Set', 5, 'Precision instruments for measuring pocket depth and detecting dental disease. 3-piece set.', 12, 45.99),

-- Grooming & Hygiene (Category 6)
('Medicated Shampoo for Dogs 16oz', 6, 'Antipruritic and antifungal shampoo for treating skin conditions and allergies.', 85, 14.99),
('Stainless Steel Nail Clippers - Large', 6, 'Heavy-duty nail clippers suitable for large breed dogs and thick nails.', 60, 16.99),
('Ear Cleaning Solution 8oz', 6, 'Gentle ear cleaner with antimicrobial properties for preventing otitis and maintaining ear health.', 95, 11.50),
('Conditioning Spray for Dogs 12oz', 6, 'Detangling and moisturizing spray for coat conditioning and easy grooming.', 70, 9.99),
('Feline Grooming Wipes - 50 count', 6, 'pH-balanced wipes for gentle cleaning and refreshing of cats between baths.', 110, 6.99),

-- Nutrition & Supplements (Category 7)
('Prescription Diet GI Digestive Care - 13.6lb Bag', 7, 'Therapeutic diet formulated for digestive support in dogs with GI sensitivities.', 35, 52.99),
('Omega-3 Fish Oil Supplement 60 capsules', 7, 'Supports joint health, skin condition, and cognitive function in pets.', 90, 19.99),
('Probiotic Powder for Pets 100g', 7, 'Beneficial bacteria to support healthy digestion and immune system. Palatable powder format.', 75, 21.99),
('Joint Support Chewable Tablet 60 count', 7, 'Glucosamine and chondroitin formulation for arthritis management and joint mobility in senior pets.', 110, 27.99),
('Prescription Diet Renal Support - 13.6lb Bag', 7, 'Low protein, low phosphorus diet designed for cats and dogs with kidney disease.', 28, 59.99),

-- Equipment & Instruments (Category 8)
('Stainless Steel Examination Table 48x24 inch', 8, 'Durable examination table with non-slip surface and folding legs for easy transport.', 5, 199.99),
('Precision Scale with Digital Display 300lb capacity', 8, 'Accurate veterinary scale for monitoring pet weight. Resolution to 0.1 lb.', 12, 129.99),
('Oxygen Delivery System with Adjustable Flow', 8, 'Complete oxygen delivery kit with mask, nasal prongs, and flow meter for anesthesia support.', 8, 89.99),
('Surgical Light with Adjustable Arm - 50W LED', 8, 'Bright adjustable surgical light providing shadow-free illumination for surgical procedures.', 4, 279.99),
('Instrument Storage Sterilizer Cabinet 35L', 8, 'Compact hot air sterilizer for sterilizing instruments. 220V electric.', 3, 449.99),

-- Orthopedic Supplies (Category 9)
('Aluminum Leg Splint Kit - Universal Fit', 9, 'Adjustable aluminum splint with foam padding for stabilizing fractures and supporting injured limbs.', 30, 34.99),
('Plaster Cast Material 3 inch - 12 rolls', 9, 'Quick-setting plaster cast wrap for secure fracture immobilization. Each roll 10 yards.', 50, 18.99),
('Elastic Support Wrap 4 inch x 5 yards', 9, 'Compression wrap for joint support and swelling reduction in limb injuries.', 80, 6.99),
('Orthopedic Dog Harness - Medium', 9, 'Specialized harness to support recovery and prevent strain during rehabilitation after surgery.', 45, 39.99),
('Therapeutic Laser Probe 50mW', 9, 'Low-level laser therapy device for pain management and accelerated wound healing.', 6, 189.99),

-- Anesthesia & Monitoring (Category 10)
('Propofol Injectable Anesthetic 10mL vial', 10, 'Ultra-short acting intravenous anesthetic agent for anesthesia induction in small animals.', 30, 34.99),
('Isoflurane Inhalant Anesthetic 250mL', 10, 'Volatile inhalant anesthetic for maintaining general anesthesia during surgical procedures.', 25, 79.99),
('Pulse Oximeter with Sensor', 10, 'Veterinary pulse oximeter for continuous monitoring of blood oxygen saturation and heart rate during anesthesia.', 18, 149.99),
('Anesthetic Mask Set - Small, Medium, Large', 10, 'Three-piece anesthetic mask set for various patient sizes with secure fit and minimal dead space.', 20, 44.99),
('Monitoring Electrodes Pack - 100 pieces', 10, 'Self-adhesive electrodes for ECG monitoring during anesthesia and cardiac assessment.', 150, 12.99);
