class Artifact {
    private final String name;
    private final int yearOfDiscovery;
    private final Person discoverer;
    private final Person curator;

    public Artifact(String name, int yearOfDiscovery, Person discoverer, Person curator) {
        this.name = name;
        this.yearOfDiscovery = yearOfDiscovery;
        this.discoverer = discoverer;
        this.curator = curator;
    }

    public String getName() {
        return name;
    }

    public int getYearOfDiscovery() {
        return yearOfDiscovery;
    }

    public Person getDiscoverer() {
        return discoverer;
    }

    public Person getCurator() {
        return curator;
    }

    @Override
    public String toString() {
        return "Artifact: " + name + " (" + yearOfDiscovery + ")\n" +
                "Discoverer: " + discoverer.toString() + "\n" +
                "Curator: " + curator.toString();
    }
}