package water.tool.util.file;

import java.io.*;

public class FileUtil {

  public static boolean write(String fName, String content) {
    return write(new File(fName), content);
  }

  /**
   * @param pr  全部
   * @param ap
   * @param len
   * @return
   */
  private static byte[] joinBytes(byte[] pr, byte[] ap, int len) {
    byte[] bytes = new byte[pr.length + len];
    System.arraycopy(pr, 0, bytes, 0, pr.length);
    System.arraycopy(ap, 0, bytes, pr.length, len);
    return bytes;
  }

  /**
   * @param file
   * @param encode
   * @return
   * @throws Exception
   */
  public static String readFile(String file, String encode) throws Exception {
    StringBuffer html = new StringBuffer();
    byte[] buffer = new byte[1024];
    InputStream iStream = new FileInputStream(file);
    try {
      while (true) {
        int len = iStream.read(buffer);
        if (len < 1) break;
        else {
          byte last = buffer[len - 1];
          if (last > 32 && last < 127) {
            html.append(new String(buffer, 0, len, encode));
          } else {
            byte[] ap = new byte[100];
            for (; ; ) {
              len = iStream.read(ap);
              if (len < 1) {
                return html.toString();
              } else {
                last = ap[len - 1];
                if (last > 32 && last < 127) {
                  html.append(new String(joinBytes(buffer, ap, len), encode));
                  break;
                } else {
                  buffer = joinBytes(buffer, ap, len);
                }
              }
            }
          }
        }

      }
    } finally {
      iStream.close();
    }
    return html.toString();
  }


  public static boolean write(File f, String content) {
    FileWriter fw = null;
    BufferedWriter bw = null;
    try {
      if (!f.exists()) {
        f.createNewFile();
      }
      fw = new FileWriter(f.getAbsoluteFile()); // true表示可以追加新内容
      bw = new BufferedWriter(fw);
      bw.write(content);
      bw.flush();
      bw.close();
      fw.close();
      return true;
    } catch (Exception e) {
      return false;
    } finally {
    }
  }

  public static void createDirectory(String fName) {
    File file = new File(fName);
    if (!file.exists())
      file.mkdir();
  }
}
