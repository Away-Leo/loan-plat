package com.pingxun.web.common.component;

import com.pingxun.biz.CwException;
import com.pingxun.core.common.fastdfs.FastDFSFile;
import com.pingxun.core.common.fastdfs.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传组件
 * Created by Administrator on 2017/6/2.
 */
@Service
public class UploadFileComponent {

    /**
     * 文件上传到fastdfs
     * @param file
     * @return
     */
    public String upload(MultipartFile file)
    {
        try {
            FileManager client = new FileManager();
            String fileName = file.getOriginalFilename();
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            FastDFSFile fastDFSFile = new FastDFSFile(fileName, file.getBytes(), prefix);
            return client.upload(fastDFSFile);
        }catch (Exception e)
        {
            CwException.throwIt("文件上传失败");
            return null;
        }
    }
}
