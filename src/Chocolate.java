public class Chocolate implements Comparable<Chocolate> {
    private String company;
    private String origin;
    private int ref;
    private int reviewDate;
    private double cocoaPercent;
    private String location;
    private double rating;

    // default constructor
    public Chocolate() {
        company = "";
        origin = "";
        ref = 0;
        reviewDate = 0;
        cocoaPercent = 0.0;
        location = "";
        rating = 0.0;
    }

    // parameterised constructor
    public Chocolate(String company, String origin, int ref, int reviewDate, double cocoaPercent, String location,
                     double rating) {
        this.company = company;
        this.origin = origin;
        this.ref = ref;
        this.reviewDate = reviewDate;
        this.cocoaPercent = cocoaPercent;
        this.location = location;
        this.rating = rating;
    }

    @Override
    public int compareTo(Chocolate other) {
        return Double.compare(this.rating, other.rating); // sort by rating
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%.2f,%s,%.2f",
                company, origin, ref, reviewDate, cocoaPercent, location, rating);
    }

    // getters
    public String getCompany() { return company; }
    public String getOrigin() { return origin; }
    public int getRef() { return ref; }
    public int getReviewDate() { return reviewDate; }
    public double getCocoaPercent() { return cocoaPercent; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
}
