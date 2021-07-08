package example.micronaut.service;


public class Bank {

    private String id;
    private String name;
    private String branch;

    public Bank(){ }


    public Bank(String id, String name, String branch){
        this.id = id;
        this.name = name;
        this.branch = branch;

    }
    public String getId(){
        return id;
    }


    public String getName(){
        return name;
    }

    public String getBranch(){
        return branch;
    }

    public void setName(String newName) {
        this.name = newName;
    }

}
