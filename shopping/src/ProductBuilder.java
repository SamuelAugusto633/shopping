public class ProductBuilder {
    private String name;

    ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    Product build() {
        return new Product(name);
    }
}






