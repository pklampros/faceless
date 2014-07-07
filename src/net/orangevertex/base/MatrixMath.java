package net.orangevertex.base;

public class MatrixMath {
    MatrixMath() {
    }

    public static double[] transpose(double[] m) {
        return new double[] {
                // @formatter:off
                m[0], m[4], m[8], m[12],
                m[1], m[5], m[9], m[13],
                m[2], m[6], m[10], m[14],
                m[3], m[7], m[11], m[15]
                // @formatter:on
        };
    }

    public static void translate(double[] m, double x, double y, double z) {
        m[12] += x;
        m[13] += y;
        m[14] += z;
    }

    public static void translatef(float[] m, float x, float y, float z) {
        m[12] += x;
        m[13] += y;
        m[14] += z;
    }

    public static void backTranslate(double[] m, double x, double y, double z) {
        m[3] += x;
        m[7] += y;
        m[11] += z;
    }

    public static void backTranslatef(float[] m, float x, float y, float z) {
        m[3] += x;
        m[7] += y;
        m[11] += z;
    }

    public static double[] getIdentity() {
        return new double[] { 
// @formatter:off
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1 
            // @formatter:on
        };
    }

    public static float[] getIdentityf() {
        return new float[] { 
// @formatter:off
            1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1 
            // @formatter:on
        };
    }

    public static double[] rotate(double[] mvp, double angle, double v0,
            double v1, double v2) {
        double norm2 = v0 * v0 + v1 * v1 + v2 * v2;
        if (norm2 < 0.000001) {
            // The vector is zero, cannot apply rotation.
            return null;
        }

        if (Math.abs(norm2 - 1) > 0.00001) {
            // The rotation vector is not normalized.
            double norm = Math.sqrt(norm2);
            v0 /= norm;
            v1 /= norm;
            v2 /= norm;
        }
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        double t = 1.0f - c;
        // @formatter:off
//      float [] rot = new float [] {
//              (t * v0 * v0) + c,          (t * v0 * v1) - (s * v2),   (t * v0 * v2)+ (s * v1),    0,
//              (t * v0 * v1) + (s * v2),   (t * v1 * v1) + c,          (t* v1 * v2)- (s * v0),     0,
//              (t * v0 * v2) - (s * v1),   (t * v1 * v2) + (s * v0),   (t * v2 * v2) + c,          0,
//              0,                          0,                          0,                          1
//      };
        double [] rot = new double [] {
                (t * v0 * v0) + c,          (t * v0 * v1) + (s * v2),   (t * v0 * v2)- (s * v1),    0,
                (t * v0 * v1) - (s * v2),   (t * v1 * v1) + c,          (t* v1 * v2)- (s * v0),     0,
                (t * v0 * v2) + (s * v1),   (t * v1 * v2)+ (s * v0),    (t * v2 * v2) + c,          0,
                0,                          0,                          0,                          1
        };
        // @formatter:on
        mvp = apply(mvp, rot);
        // mvp = apply(rot, mvp);
        return mvp;
    }

    public static float[] rotateZ(float[] mvp, double angle) {

        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        mvp = apply(mvp, new float[] {
// @formatter:off
            c,  -s, 0,  0,
            s,  c,  0,  0,
            0,  0,  1,  0,
            0,  0,  0,  1
            // @formatter:on
                });
        return mvp;
    }

    public static double[] apply(double[] m, double[] t) {
        if (t.length == 16) {
            return new double[] {
            // @formatter:off
            m[0]  * t[0]  + m[1]  * t[1]  + m[2]  * t[2]  + m[3]  * t[3],
            m[0]  * t[4]  + m[1]  * t[5]  + m[2]  * t[6]  + m[3]  * t[7],
            m[0]  * t[8]  + m[1]  * t[9]  + m[2]  * t[10] + m[3]  * t[11],
            m[0]  * t[12] + m[1]  * t[13] + m[2]  * t[14] + m[3]  * t[15],
            
            m[4]  * t[0]  + m[5]  * t[1]  + m[6]  * t[2]  + m[7]  * t[3],
            m[4]  * t[4]  + m[5]  * t[5]  + m[6]  * t[6]  + m[7]  * t[7],
            m[4]  * t[8]  + m[5]  * t[9]  + m[6]  * t[10] + m[7]  * t[11],
            m[4]  * t[12] + m[5]  * t[13] + m[6]  * t[14] + m[7]  * t[15],

            m[8]  * t[0]  + m[9]  * t[1]  + m[10] * t[2]  + m[11] * t[3],
            m[8]  * t[4]  + m[9]  * t[5]  + m[10] * t[6]  + m[11] * t[7],
            m[8]  * t[8]  + m[9]  * t[9]  + m[10] * t[10] + m[11] * t[11],
            m[8]  * t[12] + m[9]  * t[13] + m[10] * t[14] + m[11] * t[15],

            m[12] * t[0]  + m[13] * t[1]  + m[14] * t[2]  + m[15] * t[3],
            m[12] * t[4]  + m[13] * t[5]  + m[14] * t[6]  + m[15] * t[7],
            m[12] * t[8]  + m[13] * t[9]  + m[14] * t[10] + m[15] * t[11],
            m[12] * t[12] + m[13] * t[13] + m[14] * t[14] + m[15] * t[15] 
            // @formatter:on
            };
        } else if (t.length == 4) {
            return new double[] {
                // @formatter:off
                m[0]  * t[0] +  m[1]  * t[1] +  m[2]  * t[2] +  m[3]  * t[3],
                m[4]  * t[0] +  m[5]  * t[1] +  m[6]  * t[2] +  m[7]  * t[3],
                m[8]  * t[0] +  m[9]  * t[1] +  m[10] * t[2] +  m[11] * t[3],
                m[12] * t[0] +  m[13] * t[1] +  m[14] * t[2] +  m[15] * t[3]
                // @formatter:on
            };
        } else if (t.length == 1) {
            double[] res = new double[m.length];
            for (int i = 0; i < res.length; i++)
                res[i] = m[i] * t[0];
            return res;
        } else
            return null;

    }

    public static float[] apply(float[] m, float[] t) {
        if (t.length == 16) {
            return new float[] {
            // @formatter:off
            m[0]  * t[0]  + m[1]  * t[1]  + m[2]  * t[2]  + m[3]  * t[3],
            m[0]  * t[4]  + m[1]  * t[5]  + m[2]  * t[6]  + m[3]  * t[7],
            m[0]  * t[8]  + m[1]  * t[9]  + m[2]  * t[10] + m[3]  * t[11],
            m[0]  * t[12] + m[1]  * t[13] + m[2]  * t[14] + m[3]  * t[15],
            
            m[4]  * t[0]  + m[5]  * t[1]  + m[6]  * t[2]  + m[7]  * t[3],
            m[4]  * t[4]  + m[5]  * t[5]  + m[6]  * t[6]  + m[7]  * t[7],
            m[4]  * t[8]  + m[5]  * t[9]  + m[6]  * t[10] + m[7]  * t[11],
            m[4]  * t[12] + m[5]  * t[13] + m[6]  * t[14] + m[7]  * t[15],

            m[8]  * t[0]  + m[9]  * t[1]  + m[10] * t[2]  + m[11] * t[3],
            m[8]  * t[4]  + m[9]  * t[5]  + m[10] * t[6]  + m[11] * t[7],
            m[8]  * t[8]  + m[9]  * t[9]  + m[10] * t[10] + m[11] * t[11],
            m[8]  * t[12] + m[9]  * t[13] + m[10] * t[14] + m[11] * t[15],

            m[12] * t[0]  + m[13] * t[1]  + m[14] * t[2]  + m[15] * t[3],
            m[12] * t[4]  + m[13] * t[5]  + m[14] * t[6]  + m[15] * t[7],
            m[12] * t[8]  + m[13] * t[9]  + m[14] * t[10] + m[15] * t[11],
            m[12] * t[12] + m[13] * t[13] + m[14] * t[14] + m[15] * t[15] 
            // @formatter:on
            };
        } else if (t.length == 4) {
            return new float[] {
                // @formatter:off
                m[0]  * t[0] +  m[1]  * t[1] +  m[2]  * t[2] +  m[3]  * t[3],
                m[4]  * t[0] +  m[5]  * t[1] +  m[6]  * t[2] +  m[7]  * t[3],
                m[8]  * t[0] +  m[9]  * t[1] +  m[10] * t[2] +  m[11] * t[3],
                m[12] * t[0] +  m[13] * t[1] +  m[14] * t[2] +  m[15] * t[3]
                // @formatter:on
            };
        } else if (t.length == 1) {
            float[] res = new float[m.length];
            for (int i = 0; i < res.length; i++)
                res[i] = m[i] * t[0];
            return res;
        } else
            return null;

    }

    public static float[] apply(float[] m, double[] t) {
        if (t.length == 16) {
            return new float[] {
            // @formatter:off
            (float)(m[0]  * t[0]  + m[1]  * t[1]  + m[2]  * t[2]  + m[3]  * t[3]),
            (float)(m[0]  * t[4]  + m[1]  * t[5]  + m[2]  * t[6]  + m[3]  * t[7]),
            (float)(m[0]  * t[8]  + m[1]  * t[9]  + m[2]  * t[10] + m[3]  * t[11]),
            (float)(m[0]  * t[12] + m[1]  * t[13] + m[2]  * t[14] + m[3]  * t[15]),
            
            (float)(m[4]  * t[0]  + m[5]  * t[1]  + m[6]  * t[2]  + m[7]  * t[3]),
            (float)(m[4]  * t[4]  + m[5]  * t[5]  + m[6]  * t[6]  + m[7]  * t[7]),
            (float)(m[4]  * t[8]  + m[5]  * t[9]  + m[6]  * t[10] + m[7]  * t[11]),
            (float)(m[4]  * t[12] + m[5]  * t[13] + m[6]  * t[14] + m[7]  * t[15]),

            (float)(m[8]  * t[0]  + m[9]  * t[1]  + m[10] * t[2]  + m[11] * t[3]),
            (float)(m[8]  * t[4]  + m[9]  * t[5]  + m[10] * t[6]  + m[11] * t[7]),
            (float)(m[8]  * t[8]  + m[9]  * t[9]  + m[10] * t[10] + m[11] * t[11]),
            (float)(m[8]  * t[12] + m[9]  * t[13] + m[10] * t[14] + m[11] * t[15]),

            (float)(m[12] * t[0]  + m[13] * t[1]  + m[14] * t[2]  + m[15] * t[3]),
            (float)(m[12] * t[4]  + m[13] * t[5]  + m[14] * t[6]  + m[15] * t[7]),
            (float)(m[12] * t[8]  + m[13] * t[9]  + m[14] * t[10] + m[15] * t[11]),
            (float)(m[12] * t[12] + m[13] * t[13] + m[14] * t[14] + m[15] * t[15]) 
            // @formatter:on
            };
        } else if (t.length == 4) {
            return new float[] {
                // @formatter:off
            (float)(m[0]  * t[0] +  m[1]  * t[1] +  m[2]  * t[2] +  m[3]  * t[3]),
            (float)(m[4]  * t[0] +  m[5]  * t[1] +  m[6]  * t[2] +  m[7]  * t[3]),
            (float)(m[8]  * t[0] +  m[9]  * t[1] +  m[10] * t[2] +  m[11] * t[3]),
            (float)(m[12] * t[0] +  m[13] * t[1] +  m[14] * t[2] +  m[15] * t[3])
                // @formatter:on
            };
        } else if (t.length == 1) {
            float[] res = new float[m.length];
            for (int i = 0; i < res.length; i++)
                res[i] = (float) (m[i] * t[0]);
            return res;
        } else
            return null;

    }

    public static double[] rightApply(double[] m, double[] t) {
        if (t.length == 4) {
            return new double[] {
                // @formatter:off
                m[0]  * t[0] +  m[1]  * t[0] +  m[2]  * t[0] +  m[3]  * t[0],
                m[4]  * t[1] +  m[5]  * t[1] +  m[6]  * t[1] +  m[7]  * t[1],
                m[8]  * t[2] +  m[9]  * t[2] +  m[10] * t[2] +  m[11] * t[2],
                m[12] * t[3] +  m[13] * t[3] +  m[14] * t[3] +  m[15] * t[3] };
            // @formatter:on
        }
        return null;
    }

    public static double[] scale(double[] mvp, double x, double y, double z) {
        return apply(mvp, new double[] {
// @formatter:off
            x,  0,  0,  0,
            0,  y,  0,  0,
            0,  0,  z,  0,
            0,  0,  0,  1
            // @formatter:on
                });
    }

    public static float[] scalef(float[] mvp, float x, float y, float z) {
        return apply(mvp, new float[] {
// @formatter:off
            x,  0,  0,  0,
            0,  y,  0,  0,
            0,  0,  z,  0,
            0,  0,  0,  1
            // @formatter:on
                });
    }

    static void normalize(double[] vec) {
        double mag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1] + vec[2]
                * vec[2]);
        vec[0] /= mag;
        vec[1] /= mag;
        vec[2] /= mag;
    }

    static void normalize(float[] vec) {
        double mag = Math.sqrt(vec[0] * vec[0] + vec[1] * vec[1] + vec[2]
                * vec[2]);
        vec[0] /= mag;
        vec[1] /= mag;
        vec[2] /= mag;
    }

    static double[] cross(double[] v, double[] p) {
        return new double[] {  
            // @formatter:off
            v[1]*p[2] - v[2]*p[1],
            v[2]*p[0] - v[0]*p[2],
            v[0]*p[1] - v[1]*p[0]
            // @formatter:on
        };
    }

    static float[] crossf(float[] v, float[] p) {
        return new float[] {   
            // @formatter:off
            v[1]*p[2] - v[2]*p[1],
            v[2]*p[0] - v[0]*p[2],
            v[0]*p[1] - v[1]*p[0]
            // @formatter:on
        };
    }

    public static float[] lookAt(float[] eye, float[] centre, float[] up) {
        float[] Z = { centre[0] - eye[0], centre[1] - eye[1],
                centre[2] - eye[2] };
        normalize(Z);
        float[] X = crossf(up, Z);
        normalize(X);
        float[] Y = crossf(Z, X);
        normalize(Y);
        // @formatter:off
        float [] R = new float [] { 
            X[0],   Y[0],   Z[0],   0,
            X[1],   Y[1],   Z[1],   0,
            X[2],   Y[2],   Z[2],   0,
            0,  0,      0,      1
         };
        float [] E = new float [] { 
            1,      0,      0,      0,
            0,      1,      0,      0,
            0,      0,      1,      0,
            eye[0], eye[1], eye[2], 1
         };
        // @formatter:on
        R = apply(R, E);
        E = null;
        return R;
    }

    public static float[] ortho(float l, float r, float t, float b, float n,
            float f) {
        // left right top bottom near far
        // @formatter:off
        return new float [ ] {
            2.0f/(r-l),             0.0f,                   0.0f,                   0.0f,
            0.0f,                   2.0f / (t - b),         0.0f,                   0.0f,
            0.0f,                   0.0f,                   -2.0f / (f - n),        0.0f,
            -(r + l) / (r - l),     -(t + b) / (t - b),     -(f + n) / (f - n),     1.0f
            };
//      return new float [ ] {
//          2.0f/(r-l),     0.0f,               0.0f,               -(r + l) / (r - l),
//          0.0f,           2.0f / (t - b),     0.0f,               -(t + b) / (t - b),
//          0.0f,           0.0f,               -2.0f / (f - n),    -(f + n) / (f - n),
//          0,0f,           0.0f,               0.0f,               1.0f
//      };
    // @formatter:on
    }

    public static float[] perspective(float l, float r, float t, float b,
            float n, float f) {
        // left right top bottom near far
        // @formatter:off
//      return new float [] { 
//          2.0f * n / (r - l), 0,                  (r + l) / (r - l),      0,
//          0,                  2 * n / (t - b),    (t + b) / (t - b),      0,
//          0,                  0,                  -(f + n) / (f - n),     -(2 * f * n) / (f - n),
//          0,                  0,                  -1,                     0
//      };
        return new float [] { 
            2.0f * n / (r - l), 0,                  0,                      0,
            0,                  2 * n / (t - b),    0,                      0,
            (r + l) / (r - l),  (t + b) / (t - b),  -(f + n) / (f - n),     -1,
            0,                  0,                  -(2 * f * n) / (f - n), 0
        };
        // @formatter:on
    }
}
