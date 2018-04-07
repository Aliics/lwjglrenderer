package render.utils;

public class Vector3 {
    public static final Vector3 VECTOR3_ZERO = new Vector3(0, 0, 0);
    public static final Vector3 VECTOR3_ONE = new Vector3(1, 1, 1);

    private float x = 0;
    private float y = 0;
    private float z = 0;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
