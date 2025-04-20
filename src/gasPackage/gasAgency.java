package gasPackage;

public interface gasAgency {
    public String agencyName="Bharat Gas";
    public int agencyCode= 1234;
    public int phoneNumber= 914990730;

    default void display(){
        System.out.println("The agency name is: "+ agencyName);
        System.out.println("The agency code is: "+ agencyCode);
        System.out.println("The agency phone number is: "+phoneNumber);
    }
}
