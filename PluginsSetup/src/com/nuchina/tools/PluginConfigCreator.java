package com.nuchina.tools;




import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */

public class PluginConfigCreator
{

    public PluginConfigCreator()
    {
    }

    public String print(String path)
    {
        List<String> list = getFileList(path);
        if (list == null)
        {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        int length = list.size();
        for (int i = 0; i < length; i++)
        {
            String result = "";
            String thePath = getFormatPath(getString(list.get(i)));
            File file = new File(thePath);
            if (file.isDirectory())
            {
                String fileName = file.getName();
                if (fileName.indexOf("_") < 0)
                {
                    sb.append(print(thePath));
                    continue;
                }
                String[] filenames = fileName.split("_");
                String filename1 = filenames[0];
                String filename2 = filenames[1];
                result = filename1 + "," + filename2 + ",file:/" + path + "\\"
                        + fileName + "\\,4,false";
            } else if (file.isFile())
            {
                String fileName = file.getName();
                if (fileName.indexOf("_") < 0)
                {
                    continue;
                }
                int last = fileName.lastIndexOf("_");
                String filename1 = fileName.substring(0, last);
                String filename2 = fileName.substring(last + 1, fileName
                        .length() - 4);
                result = filename1 + "," + filename2 + ",file:/" + path + "\\"
                        + fileName + ",4,false";
            }
            sb.append(result+"\r\n");
        }
        return sb.toString();
    }

    public List<String> getFileList(String path)
    {
        path = getFormatPath(path);
        path = path + "/";
        File filePath = new File(path);
        if (!filePath.isDirectory())
        {
            return null;
        }
        String[] filelist = filePath.list();
        List<String> filelistFilter = new ArrayList<String>();

        for (int i = 0; i < filelist.length; i++)
        {
            String tempfilename = getFormatPath(path + filelist[i]);
            filelistFilter.add(tempfilename);
        }
        return filelistFilter;
    }

    public String getString(Object object)
    {
        if (object == null)
        {
            return "";
        }
        return String.valueOf(object);
    }

    public String getFormatPath(String path)
    {
        path = path.replaceAll("\\\\", "/");
        path = path.replaceAll("//", "/");
        return path;
    }

    public static void main(String[] args)
    {
		String plugin = "F:\\myPlugin\\MyBatis Generator\\eclipse";
        new PluginConfigCreator().print(plugin);
    }
}