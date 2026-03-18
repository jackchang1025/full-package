package com.vendor.rat.model.req;
// ADAPT: package com.guard.wallet.req -> com.vendor.rat.model.req
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.List;
public class RequestCommand implements Serializable {
    private List<String> commands;
    public RequestCommand() {
    }
    public RequestCommand(List<String> list) {
        this.commands = list;
    }
    public List<String> getCommands() {
        return this.commands;
    }
    public void setCommands(List<String> list) {
        this.commands = list;
    }
    @NonNull
    public String toString() {
        return "RequestCommand{commands=" + this.commands + '}';
    }
}
