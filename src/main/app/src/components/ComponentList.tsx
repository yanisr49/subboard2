import React, { FC } from "react";
import { TextField } from '@material-ui/core'

const ComponentList: FC = () => {
    return (
        <div>
        <TextField  label="TextField" />
            <TextField  label="TextField" variant="filled" />
            <TextField  label="TextField" />
        </div>
    );
}

export default ComponentList;