package com.junction.vk.domain;

public class ProductListItem {
    private final long listId;
    private final String title;
    private final ProductListType productListType;

    public ProductListItem(long listId, String title, ProductListType productListType) {
        this.listId = listId;
        this.title = title;
        this.productListType = productListType;
    }

    public long getListId() {
        return listId;
    }

    public String getTitle() {
        return title;
    }

    public ProductListType getProductListType() {
        return productListType;
    }

    @Override
    public String toString() {
        return "ProductListItem{" +
                "listId=" + listId +
                ", title='" + title + '\'' +
                ", productListType=" + productListType +
                '}';
    }

    public enum ProductListType {
        DEFAULT("DEFAULT"),
        CUSTOM("CUSTOM");

        private final String name;

        ProductListType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static ProductListType getProductListItemType(String name) {
            if (DEFAULT.getName().equals(name)) {
                return DEFAULT;
            }
            return CUSTOM;
        }
    }
}
