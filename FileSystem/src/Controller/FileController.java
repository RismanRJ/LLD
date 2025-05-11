package Controller;

import Components.ConcreteClasses.Directory;
import Components.ConcreteClasses.File;
import Components.FileSystemNode;

public class FileController {
    public static FileController fileController = null;

    public static FileSystemNode root;
    private FileController(){} // SingleTon

    public static FileController getInstance(){
        if(fileController==null) {
            fileController =new FileController();
            InitializeDirectory();
        }
        return fileController;

    }

    // helper functions starts
    private String[] makeFileComponentsSeparate(String path){
        String [] components = path.split("/");
        return components;
    }

    private boolean isValidPath(String path){
        return path!=null && !path.isEmpty() && path.startsWith("/");
    }
    private static void InitializeDirectory(){
        root = new Directory("/");
    }

    private String getParentPath(String path){
         int index = path.lastIndexOf("/");

         if(index<=0) return "/"; // root only exist

        return path.substring(0,index);
    }

    private FileSystemNode getNode(String path){
        if(!isValidPath(path)) return  null;

        FileSystemNode current = root;

        String[] components = makeFileComponentsSeparate(path);

        for(int i=1;i<components.length;i++){
            String component = components[i];
            if(component.isEmpty()) continue;
            if(!current.hasChild(component)) return null;


            current = current.getChild(component);
        }

        return current;

    }

    // helper functions ends
    public boolean CreateFileOrFolder(String path){
        if(!isValidPath(path)) return false;

        FileSystemNode current =root;
        String[] FileComponents = makeFileComponentsSeparate(path);
        for(int i=1;i<FileComponents.length-1;i++){
            String component = FileComponents[i];

            // if the folder doesn't exist , create a new onr
            if(!current.hasChild(component)){
               FileSystemNode newDir = new Directory(component);
               current.addChild(component,newDir);
            }

            FileSystemNode child  = current.getChild(component);

            if(child.isFile()){
                return false;
            }

            current=child;  // Navigate through the next Directory
        }


        String lastComponent = FileComponents[FileComponents.length-1];
        if(lastComponent.isEmpty()) return false;

        if(current.hasChild(lastComponent)) return false;


        FileSystemNode  newNode;

        if(lastComponent.contains(".")){
            // create a new File
            newNode = new File(lastComponent);
        }
        else{
            // create a Directory
            newNode = new Directory(lastComponent);
        }


        current.addChild(lastComponent,newNode);

        return true;

    }

    public boolean updateFileOrFolder(String oldPath,String newName){
        if(!isValidPath(oldPath)) return false;

        String parentPath = getParentPath(oldPath);
        FileSystemNode parent = getNode(parentPath);

        if(parent==null || parent.isFile()) return false;

        String ChildName = oldPath.substring(oldPath.lastIndexOf('/')+1);

        //checking if the Parent has a child or not
        if(!parent.hasChild(ChildName)) return false;

        // checking if the Parent has a child with same as new name

        if(parent.hasChild(newName)) return false;


        FileSystemNode child = parent.getChild(ChildName);

        if(child.isDeleted()) return false;
        parent.removeChild(ChildName);


        //updating the node with new Name
        child.SetName(newName);


        parent.addChild(newName,child);

        return true;
    }

    public boolean DeleteFileOrFolder(String path){
        if(!isValidPath(path)) return false;

        FileSystemNode currentNode = getNode(path);

        System.out.println(currentNode.getName());

        if(currentNode==null || path.equals("/")) return false;

        if(currentNode.isDeleted()) return false;

        currentNode.setDeleted(true);

        return true;


    }

    public boolean SetFileContent(String path, String content){

        if(!isValidPath(path))return false;

        FileSystemNode node = getNode(path);
        if(node==null || !node.isFile()) return false;

        File file =(File) node;

        file.setContent(content);
        return true;
    }

    public String  GetFileContent(String path){
        if(!isValidPath(path))return "";

        FileSystemNode node = getNode(path);
        if(node==null || !node.isFile()) return "";

        File file =(File) node;

        return file.getContent();

    }

    public void display(){
        root.display(0);
    }

    public boolean restoreDeletedFileOrFolder(String path){
        if(!(isValidPath(path))) return false;

        FileSystemNode node = getNode(path);

        if(!node.isDeleted()) return false;

        node.setDeleted(false);

        return true;
    }

}
