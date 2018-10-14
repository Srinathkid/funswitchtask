package com.srinath.funswitchtask;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {

    private Integer id;

    private String description;

    private String scheduledDate;

    private String status;

    protected Model(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        description = in.readString();
        scheduledDate = in.readString();
        status = in.readString();
    }

    public static final Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel in) {
            return new Model(in);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(description);
        dest.writeString(scheduledDate);
        dest.writeString(status);
    }
}
