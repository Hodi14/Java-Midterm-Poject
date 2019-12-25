package com.company;

import java.util.*;

public class Main {
    static class Node {
        Node parent;
        String name = "", condition = "";
        int level = 0, relation = 0;

        Node(String s, Node p, int r) {
            this.name = s;
            this.parent = p;
            this.relation = r;
            if (this.parent != null)
                this.level = (this.parent).level + 1;
        }
    }

    static class N_list {
        public static ArrayList<Node> node_list = new ArrayList<>();
        public static ArrayList<String> answer = new ArrayList<>();
    }

    public static void input1_parse(String s) {
        String[] command = new String[0], mandatory = new String[0], or = new String[0], xor = new String[0];
        s = s.trim().replaceAll("\\s+", "");
        Node root = null;
        if (!(s.contains("="))) {
            root = new Node(s, null, -1);
            root.level = 0;
            N_list.node_list.add(root);
        }
        else {
            command = s.split("=", 2);
            String children = command[1];
            if (N_list.node_list.size() == 0) {
                root = new Node(command[0], null, -1);
                root.level = 0;
                N_list.node_list.add(root);
            }
            else {
                boolean is_new = true;
                for (int i = 0; i < N_list.node_list.size(); i++)
                    if (N_list.node_list.get(i).name.equals(command[0])) {
                        is_new = false;
                        root = N_list.node_list.get(i);
                    }
                if (is_new == true) {
                    root = new Node(command[0], null, -1);
                    root.level = 0;
                    N_list.node_list.add(root);
                }
            }

            if (children.contains("+")) {
                mandatory = children.split("\\+", 0);
                for (int i = 0; i < mandatory.length; i++) {
                    boolean is_new = true;
                    for (int j = 0; j < N_list.node_list.size(); j++) {
                        if (mandatory[i].equals(N_list.node_list.get(j).name)) {
                            is_new = false;
                            N_list.node_list.get(j).parent = root;
                            N_list.node_list.get(j).relation = 0;
                            N_list.node_list.get(j).level = root.level + 1;
                            break;
                        }
                        if (mandatory[i].equals('?' + N_list.node_list.get(j).name)) {
                            is_new = false;
                            N_list.node_list.get(j).parent = root;
                            N_list.node_list.get(j).relation = 3;
                            N_list.node_list.get(j).level = root.level + 1;
                            break;
                        }
                    }

                    if (is_new == false) {
                        if (mandatory[i].charAt(0) == '?')
                            change_level(mandatory[i].substring(1, mandatory[i].length()));
                        else
                            change_level(mandatory[i]);
                    }
                    if (is_new == true) {
                        for (int j = 0; j < N_list.node_list.size(); j++)
                            if ((N_list.node_list.get(j).name).equals(command[0])) {
                                Node child = new Node(mandatory[i], N_list.node_list.get(j), 0);
                                if (mandatory[i].charAt(0) == '?') {
                                    child.relation = 3;
                                    child.name = mandatory[i].substring(1, mandatory[i].length());
                                }
                                N_list.node_list.add(child);
                                break;
                            }
                    }
                }
            }
            if (children.contains("|")) {
                or = children.split("\\|", 0);
                for (int i = 0; i < or.length; i++) {
                    boolean is_new = true;
                    for (int j = 0; j < N_list.node_list.size(); j++) {
                        if (or[i].equals(N_list.node_list.get(j).name)) {
                            is_new = false;
                            N_list.node_list.get(j).parent = root;
                            N_list.node_list.get(j).relation = 1;
                            N_list.node_list.get(j).level = root.level + 1;
                            break;
                        }
                    }
                    if (is_new == false)
                        change_level(or[i]);

                    if (is_new == true) {
                        for (int j = 0; j < N_list.node_list.size(); j++)
                            if ((N_list.node_list.get(j).name).equals(command[0])) {
                                Node child = new Node(or[i], N_list.node_list.get(j), 1);
                                N_list.node_list.add(child);
                                break;
                            }
                    }
                }
            }
            if (children.contains("^")) {
                xor = children.split("\\^", 0);
                for (int i = 0; i < xor.length; i++) {
                    boolean is_new = true;
                    for (int j = 0; j < N_list.node_list.size(); j++) {
                        if (xor[i].equals(N_list.node_list.get(j).name)) {
                            is_new = false;
                            N_list.node_list.get(j).parent = root;
                            N_list.node_list.get(j).relation = 2;
                            N_list.node_list.get(j).level = root.level + 1;
                            break;
                        }
                    }
                    if (is_new == false)
                        change_level(xor[i]);

                    if (is_new == true) {
                        for (int j = 0; j < N_list.node_list.size(); j++)
                            if ((N_list.node_list.get(j).name).equals(command[0])) {
                                Node child = new Node(xor[i], N_list.node_list.get(j), 2);
                                N_list.node_list.add(child);
                                break;
                            }
                    }
                }
            }
            if (!((children.contains("^") || (children.contains("|")) || (children.contains("+"))))) {
                boolean is_new = true;
                for (int i = 0; i < N_list.node_list.size(); i++) {
                    if (N_list.node_list.get(i).name.equals(children)) {
                        is_new = false;
                        N_list.node_list.get(i).parent = root;
                        N_list.node_list.get(i).relation = 0;
                        N_list.node_list.get(i).level = root.level + 1;
                        break;
                    }
                    if (('?' + N_list.node_list.get(i).name).equals(children)) {
                        is_new = false;
                        N_list.node_list.get(i).parent = root;
                        N_list.node_list.get(i).relation = 3;
                        N_list.node_list.get(i).level = root.level + 1;
                        break;
                    }
                }
                if (is_new == false) {
                    if (children.charAt(0) == '?')
                        change_level(children.substring(1, children.length()));
                    else
                        change_level(children);
                }
                if (is_new == true) {
                    for (int i = 0; i < N_list.node_list.size(); i++) {
                        if ((N_list.node_list.get(i).name).equals(command[0])) {
                            Node child = new Node(children, N_list.node_list.get(i), 0);
                            if (children.charAt(0) == '?') {
                                child.relation = 3;
                                child.name = children.substring(1, children.length());
                            }
                            N_list.node_list.add(child);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void input2_parse(String s) {
        String[] test = new String[0];
        s = s.trim().replaceAll("\\s+", "");
        s = s.substring(1, s.length() - 1);
        test = s.split(",", 0);
        N_list.answer.add(check(test));
    }

    public static void input1_convert(ArrayList<String> arr1) {
        for (int i = 0; i < arr1.size(); i++)
            input1_parse(arr1.get(i));
    }

    public static void input2_convert(ArrayList<String> arr2) {
        for (int i = 0; i < arr2.size(); i++) {
            input2_parse(arr2.get(i));
            for (int j = 0; j < N_list.node_list.size(); j++)
                N_list.node_list.get(j).condition = "";
        }
    }

    public static void change_level(String p) {
        for (int i = 0; i < N_list.node_list.size(); i++)
            if (N_list.node_list.get(i).parent != null && p.equals(N_list.node_list.get(i).parent.name)) {
                N_list.node_list.get(i).level = N_list.node_list.get(i).parent.level + 1;
                change_level(N_list.node_list.get(i).name);
            }
    }

    public static String check(String[] arr) {
        int start_level = 0;
        for (int i = 0; i < N_list.node_list.size(); i++)
            start_level = Math.max(start_level, N_list.node_list.get(i).level);

        for (int i = start_level; i >= 0; i--) {
            for (int j = 0; j < N_list.node_list.size(); j++) {
                Node c_node = N_list.node_list.get(j);
                int r = c_node.relation;
                boolean found_same = false;
                if (c_node.level == i) {
                    for (int k = 0; k < arr.length; k++) {
                        if (arr[k].equals(c_node.name)) {
                            found_same = true;
                            if (r == -1) {
                                if (c_node.condition.equals("unused"))
                                    return "Invalid";
                                else
                                    c_node.condition = "used";
                            }
                            if (r == 0 || r == 3 || r == 1) {
                                if (c_node.condition.equals("unused") || c_node.parent.condition.equals("unused"))
                                    return "Invalid";
                                else {
                                    c_node.condition = "used";
                                    (c_node.parent).condition = "used";
                                }
                                break;
                            }
                            if (r == 2) {
                                if (c_node.condition.equals("unused") || c_node.parent.condition.equals("unused"))
                                    return "Invalid";
                                else {
                                    (c_node.parent).condition = "used";
                                    for (int l = 1; l < N_list.node_list.size(); l++)
                                        if (N_list.node_list.get(l).parent.equals(c_node.parent) && N_list.node_list.get(l).relation == 2)
                                            N_list.node_list.get(l).condition = "unused";
                                    c_node.condition = "used";
                                }
                                break;
                            }
                            break;
                        }
                    }

                    if (!found_same) {
                        if (c_node.condition.equals("used") || c_node.parent == null)
                            return "Invalid";
                        else if (c_node.parent.condition.equals("used") && r == 0)
                            return "Invalid";
                        else {
                            if (r == -1)
                                return "Invalid";
                            if (r == 0) {
                                c_node.condition = "unused";
                                (c_node.parent).condition = "unused";
                            }
                            else
                                c_node.condition = "unused";
                        }
                    }
                }
            }
        }

        for (int i = 0; i < N_list.node_list.size(); i++) {
            Node c_node = N_list.node_list.get(i);
            int or_child = 0, xor_child = 0;
            boolean has_or_child = false, has_xor_child = false;
            if (c_node.condition.equals("used")) {
                for (int l = 1; l < N_list.node_list.size(); l++) {
                    Node child = N_list.node_list.get(l);
                    if (child.parent.equals(c_node)) {
                        if (child.relation == 2) {
                            if (child.condition.equals("used"))
                                xor_child++;
                            has_xor_child = true;
                        }
                        if (child.relation == 1) {
                            if (child.condition.equals("used"))
                                or_child++;
                            has_or_child = true;
                        }
                    }
                }
            }
            if ((has_or_child && or_child == 0) || (has_xor_child && xor_child != 1))
                return "Invalid";
        }
        return "Valid";
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command = "";
        boolean input1_done = false;
        ArrayList<String> input1 = new ArrayList<>();
        ArrayList<String> input2 = new ArrayList<>();

        while (!(command.equals("###"))) {
            command = input.nextLine();
            if (!(command.equals("#")) && !input1_done)
                input1.add(command);
            if (command.equals("#")) {
                input1_convert(input1);
                input1.removeAll(input1);
                input1_done = true;
            }

            if (!(command.equals("##")) && !(command.equals("#")) && input1_done)
                input2.add(command);
            if (command.equals("##")) {
                input2_convert(input2);
                N_list.answer.add("+++");
                input2.removeAll(input2);
                N_list.node_list.removeAll(N_list.node_list);
                input1_done = false;
            }
        }
        for (int i = 0; i < N_list.answer.size(); i++)
            System.out.println(N_list.answer.get(i));
    }
}