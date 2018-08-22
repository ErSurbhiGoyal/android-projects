INTENT

An Intent is a messaging object you can use to request an action from another app component. Although intents facilitate communication between components in several ways, there are three fundamental use cases:
1. Starting an activity
2. Starting a service
3. Delivering a broadcast

These use cases will be discussed later on. Let's now discuss different types of Intents.

Types of Intents:-

There are basically two types of intents:-
1. Explicit Intent
2. Implicit Intent

1. Explicit Intent:- An explicit intent is one that you use to launch a specific app component, such as a particular activity or service in your app. To create an explicit intent, define the component name for the Intent object—all other intent properties are optional.

Example:- If your application has some activity with name, say, HomePage then you can open that activity using explicit intent by using this code:-

    // 'this' is the Context,referring to the activity in which the operation is getting performed
    // HomePage is the activity we want to open
    Intent homePageIntent = new Intent(this, HomePage.class);
    startActivity(homePageIntent);

2. Implicit Intent:- An implicit intent specifies an action that can invoke any app on the device able to perform the action. Using an implicit intent is useful when your app cannot perform the action, but other apps probably can and you'd like the user to pick which app to use.

Example:- If you want to open gallery in your app. You can use the following code to open the gallery.

    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Select Image"),SELECT_IMAGE);

You can use the following code to get image

    public void onActivityResult(int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == SELECT_IMAGE){
        if (resultCode == Activity.RESULT_OK){
            if (data != null){
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
