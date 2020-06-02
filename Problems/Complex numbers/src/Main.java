class Complex {

    double real;
    double image;

    void add(Complex num) {
        real += num.real;
        image += num.image;
    }

    void subtract(Complex num) {
        real -= num.real;
        image -= num.image;
    }
}