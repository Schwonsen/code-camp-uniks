package com.uni.cc_uniapp_2015.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Schrom on 31.01.2015.
 */
public class ImageHelper {


    public static Bitmap getBitmapForAbsolutePath(String filePath){
    try {
    File imgFile = new File(filePath);

    if (imgFile.exists()) {

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        return myBitmap;

    }}
    catch(Exception e){
        e.printStackTrace();
    }
    return null;
    }

    public static Drawable getDrawableForAbsolutePath(Context context, String filePath){
        try {
            File imgFile = new File(filePath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Drawable myDrawable = new BitmapDrawable(context.getResources(), myBitmap);
                return myDrawable;

            }}
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable getDrawableForAbsolutePathOrDefaultFromFolder(Context context, String filePath,String locationOfDefaultImage,String nameOfDefaultImage){
        try {
            File imgFile = new File(filePath);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                Drawable myDrawable = new BitmapDrawable(context.getResources(), myBitmap);
                return myDrawable;

            }
            else{
                Drawable myDrawable = getDrawableFromFolder(context,locationOfDefaultImage,nameOfDefaultImage);
                return myDrawable;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable getDrawableForAbsolutePath(String filePath) {
        try {
        Drawable d = Drawable.createFromPath(filePath);
            return d;
        }catch (Exception e){
            System.out.print("failed to get drawable from filepath "+filePath);
        }
        return null;
    }

    public static Drawable saveImageFromBitmapString(Context context, String imageName, String bitMapAsString, String filePath){
        try {
            Bitmap myBitmap=getBitmapFromString(context, bitMapAsString);
            Drawable myDrawable = new BitmapDrawable(context.getResources(), myBitmap);
            saveImage(context,myBitmap,imageName,filePath);
            return myDrawable;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static Bitmap getBitmapFromString(Context context, String bitMapAsString){
        try {
            byte [] encodeByte=android.util.Base64.decode(bitMapAsString,android.util.Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    //copied from file writing helper
    public static void saveImage(Context context,Bitmap finalBitmap,String fileName,String location) {
        //Toast.makeText(context,"starting to save image",Toast.LENGTH_SHORT).show();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + location);
        myDir.mkdirs();

        File file = new File (myDir, fileName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Toast.makeText(context,finalBitmap+" "+Bitmap.CompressFormat.JPEG+" "+out,Toast.LENGTH_SHORT).show();
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.print("failed to save image");
            //Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            //Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }


    public static String getBitMapAsString(Bitmap myBitmap){
        String myBitmapString = "";
        try {

            // Add your data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            myBitmapString = android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT);
        }catch (Exception e){
            System.out.print("failed to get bitmap as string");
            e.printStackTrace();
        }
        return myBitmapString;
    }


    public static Bitmap getRoundedBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }

        return bitmap;
    }
//not tested or used
    public static Drawable getRoundedDrawableFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(filePath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            // handle exception
        }
        Bitmap roundedBitmap = getRoundedBitmap(bitmap, 100);

        Drawable myRoundedDrawable = getDrawableFromBitmap(context,roundedBitmap);
        return myRoundedDrawable;
    }


    public static Drawable getDrawableFromBitmap(Context context, Bitmap bitmap) {
        Drawable myDrawable = null ;
        try {
            myDrawable = new BitmapDrawable(context.getResources(), bitmap);
        }catch (Exception e){
            System.out.print("failed to create drawable from bitmap");
            e.printStackTrace();
        }
        return myDrawable;
    }

    public static Drawable getRoundedDrawableFromFolder(Context context, String location ,String imageName,int roundedPixels) {

        Drawable myRoundedDrawable = null;
        try {
            int resourceIdOfImage = context.getResources().getIdentifier(imageName, location, context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceIdOfImage);

        Bitmap roundedBitmap = getRoundedBitmap(bitmap, roundedPixels);

        myRoundedDrawable = getDrawableFromBitmap(context,roundedBitmap);
        } catch (Exception e) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            System.out.print("failed to get rounded drawable from folder ");
            e.printStackTrace();
        }
        return myRoundedDrawable;
    }


    public static int getImageResource (String titleImageName,String folderPath,Context context){
        Integer resourceId = null;
        try {
            resourceId=context.getResources().getIdentifier(titleImageName, folderPath, context.getPackageName());
        }catch (Exception e){
            System.out.print("failed to get resource id");
            e.printStackTrace();
        }
        return resourceId;
    }

    public static int getLowQualityImageResourceIfAvailable (String titleImageName,String folderPath,Context context){
        Integer resourceId = null;
        int imageExists = context.getResources().getIdentifier(titleImageName+"_lq", folderPath, context.getPackageName());
        if(imageExists!=0) {
            resourceId = getImageResource(titleImageName + "_lq", folderPath, context);
            //Toast.makeText(context,titleImageName+":"+resourceId,Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(context,titleImageName+":"+resourceId,Toast.LENGTH_SHORT).show();

                resourceId = getImageResource(titleImageName, folderPath, context);
            }

        return resourceId;
    }




    public static Drawable getDrawableFromFolder(Context context, String location ,String imageName) {

        Drawable myRoundedDrawable = null;
        try {
            int resourceIdOfImage = context.getResources().getIdentifier(imageName, location, context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceIdOfImage);

            myRoundedDrawable = getDrawableFromBitmap(context,bitmap);
        } catch (Exception e) {
            //Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            System.out.print("failed to get rounded drawable from folder ");
            e.printStackTrace();
        }catch (OutOfMemoryError e) {
            //Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            System.out.print("failed to get rounded drawable from folder ");
            e.printStackTrace();
        }
        return myRoundedDrawable;
    }


    public static Bitmap getBitmapFromFolder(Context context, String location ,String imageName) {

        Bitmap bitmap = null;
        try {
            int resourceIdOfImage = context.getResources().getIdentifier(imageName, location, context.getPackageName());
            bitmap = BitmapFactory.decodeResource(context.getResources(), resourceIdOfImage);


        } catch (Exception e) {
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
            System.out.print("failed to get bitmap from folder ");
            e.printStackTrace();
        }
        return bitmap;
    }
    public static BitmapDrawable getBitmapDrawableFromFolder(Context context, String location ,String imageName) {
        Bitmap bitmap = getBitmapFromFolder(context, location ,imageName);
        BitmapDrawable myBitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        return myBitmapDrawable;
    }

    public static BitmapDrawable getGrayFilteredBitmapDrawableFromFolder(Context context, String location ,String imageName) {
        ColorMatrix grayMatrix = new ColorMatrix();
        grayMatrix.setSaturation(0);
        ColorMatrixColorFilter grayscaleFilter = new ColorMatrixColorFilter(grayMatrix);
        BitmapDrawable bitmapDrawable = getBitmapDrawableFromFolder(context, location ,imageName);
        bitmapDrawable.setColorFilter(grayscaleFilter);
        return bitmapDrawable;
    }
}
