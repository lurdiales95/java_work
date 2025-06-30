record Movie(String title, String genre, String director, double rating, int releaseYear) {

    public void print() {
        System.out.printf("%-30s | %-10s | %-25s | %6.1f | %d%n",
                title(),
                genre(),
                director(),
                rating(),
                releaseYear());
    }

}