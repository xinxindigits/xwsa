package cn.com.xinxin.sass.sal.wesdk;


public class Finance {
    public native static long NewSdk();


    public native static int Init(long sdk, String corpid, String secret);


    public native static int GetChatData(long sdk, long seq, long limit, String proxy, String passwd, long timeout, long chatData);


    public native static int GetMediaData(long sdk, String indexbuf, String sdkField, String proxy, String passwd, long timeout, long mediaData);


    public native static int DecryptData(long sdk, String encrypt_key, String encrypt_msg, long msg);
	
    public native static void DestroySdk(long sdk);
    public native static long NewSlice();
    /**
     * @brief �ͷ�slice����NewSlice�ɶ�ʹ��
     * @return 
     */
    public native static void FreeSlice(long slice);

    /**
     * @brief ��ȡslice����
     * @return ����
     */
    public native static String GetContentFromSlice(long slice);

    /**
     * @brief ��ȡslice���ݳ���
     * @return ����
     */
    public native static int GetSliceLen(long slice);
    public native static long NewMediaData();
    public native static void FreeMediaData(long mediaData);

    /**
     * @brief ��ȡmediadata outindex
     * @return outindex
     */
    public native static String GetOutIndexBuf(long mediaData);
    /**
     * @brief ��ȡmediadata data����
     * @return data
     */
    public native static byte[] GetData(long mediaData);
    public native static int GetIndexLen(long mediaData);
    public native static int GetDataLen(long mediaData);

    /**
     * @brief �ж�mediadata�Ƿ����
     * @return 1��ɡ�0δ���
     */
    public native static int IsMediaDataFinish(long mediaData);

    static {
        System.loadLibrary("WeWorkFinanceSdk_Java");
    }
}
