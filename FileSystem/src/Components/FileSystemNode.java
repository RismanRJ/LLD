package Components;

import Components.ConcreteClasses.File;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class FileSystemNode {
    private String name;
    private Map<String,FileSystemNode> children;
    private boolean isDeleted;

    public String getName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public FileSystemNode(String name) {
        this.name = name;
        children = new LinkedHashMap<>(); // to maintain the order of creation
    }


    public boolean hasChild(String child){
        return this.children.containsKey(child);
    }

    public FileSystemNode getChild(String childName){
        if(!hasChild(childName)) return null;
        return this.children.get(childName);
    }


    public abstract boolean isFile();
    public abstract void display(int depth);

    public void addChild(String childName, FileSystemNode child){
        this.children.put(childName,child);
    }

    public void removeChild(String childName){
        this.children.remove(childName);
    }

    public Collection<FileSystemNode> getChildren(){
        return this.children.values();
    }
}
