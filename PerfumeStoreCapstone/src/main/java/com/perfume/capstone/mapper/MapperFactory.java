package com.perfume.capstone.mapper;

public class MapperFactory {

    // Singleton instances for performance
    private static final PerfumeRowMapper PERFUME_MAPPER = new PerfumeRowMapper();
    private static final InventoryPerfumeItemRowMapper INVENTORY_MAPPER = new InventoryPerfumeItemRowMapper();
    private static final CartItemRowMapper CART_ITEM_MAPPER = new CartItemRowMapper();
    private static final InventoryStatsRowMapper INVENTORY_STATS_MAPPER = new InventoryStatsRowMapper();

    /**
     * Get mapper for Perfume objects
     */
    public static PerfumeRowMapper perfumeMapper() {
        return PERFUME_MAPPER;
    }

    /**
     * Get mapper for InventoryPerfumeItem objects
     */
    public static InventoryPerfumeItemRowMapper inventoryMapper() {
        return INVENTORY_MAPPER;
    }

    /**
     * Get mapper for CartItem objects
     */
    public static CartItemRowMapper cartItemMapper() {
        return CART_ITEM_MAPPER;
    }

    /**
     * Get mapper for inventory statistics
     */
    public static InventoryStatsRowMapper inventoryStatsMapper() {
        return INVENTORY_STATS_MAPPER;
    }
}