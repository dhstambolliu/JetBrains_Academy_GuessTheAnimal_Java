package animals.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode {
    private String data;
    private TreeNode yes;
    private TreeNode no;

    TreeNode() {
    }

    public TreeNode(final String data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isLeaf() {
        return no == null && yes == null;
    }

    public String getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data.toString();
    }

    public TreeNode getYes() {
        return yes;
    }

    public void setYes(TreeNode yes) {
        this.yes = yes;
    }

    public TreeNode getNo() {
        return no;
    }

    public void setNo(TreeNode no) {
        this.no = no;
    }
}