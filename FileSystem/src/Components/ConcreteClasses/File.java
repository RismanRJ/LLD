package Components.ConcreteClasses;

import Components.FileSystemNode;

public class File extends FileSystemNode {
    private String content;
    private String extension;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File(String name) {
        super(name);
        this.extension =getExtension(name);
    }

    private String getExtension(String name){
        int index = name.lastIndexOf('.');
        return index>0? name.substring(index+1):"";
    }


    @Override
    public boolean isFile() {
        return true;
    }

    @Override
    public void display(int depth) {
        if(isDeleted()) return;
        // Example: For a file at path "/document/cwa_lld/requirements.txt" at depth 3
        // indent = "      " (6 spaces: depth 3 * 2 spaces per depth)
        // Output would be: "      📄 requirements.txt"
        // For our example, if depth is 3 (meaning this file is at the 3rd level)
        // Generate indent string of 6 spaces (3*2)
        String indent = " ".repeat(depth * 2);
        // Print the file with appropriate indentation and emoji
        // e.g., "      📄 requirements.txt"
        System.out.println(indent + "📄 " + getName());
    }
}
