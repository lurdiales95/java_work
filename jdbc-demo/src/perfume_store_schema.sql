-- ===============================================
-- SIMPLE PERFUME STORE DATABASE SCHEMA
-- Based exactly on your existing models with integer IDs
-- ===============================================

-- Drop database if exists (for clean setup)
DROP DATABASE IF EXISTS perfume_store;

-- Create database
CREATE DATABASE perfume_store;
USE perfume_store;

-- ===============================================
-- CORE TABLES - MATCHING YOUR EXISTING MODELS
-- ===============================================

/**
 * PERFUMES TABLE
 * Maps directly to your Perfume record
 * Only the 4 fields you already have + integer ID
 */
CREATE TABLE perfumes (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,           -- Your productName field  
    scent_type VARCHAR(50) NOT NULL,              -- Your scentType field
    season_availability VARCHAR(20) NOT NULL,     -- Your seasonAvailability field
    
    -- Indexes for performance
    INDEX idx_scent_type (scent_type),
    INDEX idx_season (season_availability)
);

/**
 * PERFUME_INVENTORY TABLE  
 * Maps directly to your InventoryPerfumeItem class
 * Contains: perfume reference, quantity, price
 */
CREATE TABLE perfume_inventory (
    product_id INT PRIMARY KEY,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL,
    
    -- Foreign key to perfumes
    CONSTRAINT fk_inventory_perfume 
        FOREIGN KEY (product_id) REFERENCES perfumes(product_id) 
        ON DELETE CASCADE,
        
    -- Business rules (same as your existing validation)
    CONSTRAINT chk_quantity_positive CHECK (quantity >= 0),
    CONSTRAINT chk_price_positive CHECK (price >= 0)
);

/**
 * CART_ITEMS TABLE
 * Replaces your in-memory HashMap<String, CartItem> cart
 * Maps to your CartItem class structure
 */
CREATE TABLE cart_items (
    cart_item_id INT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(100) NOT NULL,             -- Identifies which cart (user session)
    product_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL,           -- Price when added to cart
    total_price DECIMAL(10, 2) NOT NULL,          -- quantity * unit_price (renamed from extendedPrice)
    
    -- Foreign key to perfumes
    CONSTRAINT fk_cart_product 
        FOREIGN KEY (product_id) REFERENCES perfumes(product_id) 
        ON DELETE CASCADE,
        
    -- Prevent duplicate products in same cart
    UNIQUE KEY unique_session_product (session_id, product_id),
    
    -- Business rules (same as your CartItem validation)
    CONSTRAINT chk_cart_quantity_positive CHECK (quantity > 0),
    CONSTRAINT chk_cart_price_positive CHECK (unit_price >= 0),
    CONSTRAINT chk_total_price_positive CHECK (total_price >= 0),
    
    INDEX idx_session_items (session_id)
);

-- ===============================================
-- SAMPLE DATA - YOUR EXISTING PERFUME DATA
-- ===============================================

-- Insert your existing perfumes (will get auto-increment IDs 1-25)
INSERT INTO perfumes (product_name, scent_type, season_availability) VALUES
-- Botanical & Blooms - Floral scented
('Japanese Cherry Blossom', 'Botanical & Blooms', 'Year Round'),
('Fresh Cut Lilacs', 'Botanical & Blooms', 'Spring'),
('Ghoul Friend', 'Botanical & Blooms', 'Fall'),
('Moonlight Path', 'Botanical & Blooms', 'Winter'),
('Orange Lily Bloom', 'Botanical & Blooms', 'Summer'),

-- Fresh & Clean - Clean scents
('Sweater Weather', 'Fresh & Clean', 'Winter'),
('Eucalyptus Mint', 'Fresh & Clean', 'Year Round'),
('Sweet Tea & Lemonade', 'Fresh & Clean', 'Summer'),
('Mountainside Mist', 'Fresh & Clean', 'Winter'),
('Vanilla Breeze', 'Fresh & Clean', 'Spring'),

-- Fruity & Bright - Citrus scents
('Honeycrisp Apple', 'Fruity & Bright', 'Year Round'),
('Raspberry Mimosa', 'Fruity & Bright', 'Summer'),
('Midnight Blue Citrus', 'Fruity & Bright', 'Year Round'),
('Candy Apple Cauldron', 'Fruity & Bright', 'Fall'),
('Vampire Blood', 'Fruity & Bright', 'Fall'),

-- Treats & Sweets - Dessert scents
('Campfire Cocoa', 'Treats & Sweets', 'Winter'),
('Pumpkin Pecan Waffles', 'Treats & Sweets', 'Fall'),
('Pumpkin Cinnamon Bun', 'Treats & Sweets', 'Fall'),
('Praline Delight', 'Treats & Sweets', 'Fall'),
('I Scream Float', 'Treats & Sweets', 'Fall'),

-- Warm & Woodsy - Cologne-like scents
('Leather & Brandy', 'Warm & Woodsy', 'Year Round'),
('Palo Santo', 'Warm & Woodsy', 'Spring'),
('Sunrise Woods', 'Warm & Woodsy', 'Summer'),
('Into the Night', 'Warm & Woodsy', 'Winter'),
('Vanilla Ease', 'Warm & Woodsy', 'Spring');

-- Insert inventory data (matches your existing quantities and prices)
INSERT INTO perfume_inventory (product_id, quantity, price) VALUES
(1, 150, 26.95),   -- Japanese Cherry Blossom
(2, 150, 10.99),   -- Fresh Cut Lilacs
(3, 150, 26.95),   -- Ghoul Friend
(4, 150, 26.95),   -- Moonlight Path
(5, 150, 10.99),   -- Orange Lily Bloom
(6, 150, 26.95),   -- Sweater Weather
(7, 150, 26.95),   -- Eucalyptus Mint
(8, 150, 26.95),   -- Sweet Tea & Lemonade
(9, 150, 26.96),   -- Mountainside Mist
(10, 150, 26.95),  -- Vanilla Breeze
(11, 150, 16.95),  -- Honeycrisp Apple
(12, 150, 26.95),  -- Raspberry Mimosa
(13, 150, 26.95),  -- Midnight Blue Citrus
(14, 150, 39.95),  -- Candy Apple Cauldron
(15, 150, 39.95),  -- Vampire Blood
(16, 150, 26.95),  -- Campfire Cocoa
(17, 150, 26.95),  -- Pumpkin Pecan Waffles
(18, 150, 26.95),  -- Pumpkin Cinnamon Bun
(19, 150, 24.95),  -- Praline Delight
(20, 150, 26.95),  -- I Scream Float
(21, 150, 26.95),  -- Leather & Brandy
(22, 150, 26.96),  -- Palo Santo
(23, 150, 24.95),  -- Sunrise Woods
(24, 150, 26.95),  -- Into the Night
(25, 150, 26.95);  -- Vanilla Ease

-- ===============================================
-- USEFUL QUERIES FOR TESTING
-- ===============================================

-- View all perfumes with inventory
SELECT 
    p.product_id,
    p.product_name,
    p.scent_type,
    p.season_availability,
    pi.quantity,
    pi.price
FROM perfumes p
JOIN perfume_inventory pi ON p.product_id = pi.product_id
ORDER BY p.product_id;

-- Check database setup
SELECT 'Simple perfume store database created successfully!' AS status;
SELECT COUNT(*) as total_perfumes FROM perfumes;
SELECT COUNT(*) as inventory_items FROM perfume_inventory;cart_itemsperfume_inventoryperfumes